package collections.implementations;

import java.util.function.Function;
import java.util.function.UnaryOperator;

// TODO: double pointer
public final class VersionedDoubleLinkedList<E> {

	private ListCell<E> firstCell;
	private int version;

	public VersionedDoubleLinkedList() {
		this(new NilCell<E>(), 0);
	}

	public VersionedDoubleLinkedList(ListCell<E> newFirstCell, int version) {
		this.version   = version;
		this.firstCell = newFirstCell;
	}

	public ListCell<E> getFirstCell(){
		return this.firstCell;
	}

	public int getVersion() {
		return this.version;
	}

	private void checkVersion(int clientVersion) {
		if (clientVersion == this.version)
			return;
		throw new UnsupportedOperationException("Version is outdated. Found: " +
							clientVersion                  +
							" - Expected: "                +
							this.version);
	}

	public void addFirst(E head, ListCell<E> cell, int clientVersion) {
		this.checkVersion(clientVersion);
		if (cell.isFirst()) {
			this.firstCell = new ConsCell<E>(head, cell);
		} else {
			this.version++;
			cell.getPrevious().setItem(head);
		}
	}

	public ListCell<E> tail(ListCell<E> thisCell, int thisVersion) {
		this.checkVersion(thisVersion);
		return thisCell.getNext();
	}

	public E head(ListCell<E> thisCell, int thisVersion) {
		this.checkVersion(thisVersion);
		return thisCell.getItem();
	}

	public ListCell<E> concatenate(ListCell<E> thisCell,
				       int thisVersion,
				       VersionedDoubleLinkedList<E> other,
				       ListCell<E> otherCell,
				       int otherVersion) {
		this.checkVersion(thisVersion);
		other.checkVersion(otherVersion);

		if (otherCell.isEmpty())
			return thisCell;

		if (thisCell.isEmpty()) {
			this.version++;
			this.firstCell = otherCell.clone(); // TODO: do not copy
			return this.firstCell;
		} else {
			this.version++;
			this.firstCell.concat(otherCell);
			return thisCell;
		}
	}

	public void reverse(ListCell<E> thisCell, int thisVersion) {
		this.checkVersion(thisVersion);
		this.version++;
		thisCell.reverse(firstCell.previous);
	}

	public boolean isEmpty(ListCell<E> cell, int thisVersion) {
		this.checkVersion(thisVersion);
		return cell.isEmpty();
	}

	public <R> VersionedDoubleLinkedList<R> map(ListCell<E> thisCell, int thisVersion, Function<E, R> mapper) {
		this.checkVersion(thisVersion);
		ListCell<R> newCell = thisCell.map(mapper);
		return new VersionedDoubleLinkedList<R>(newCell, 0);
	}

	public void applyMap(ListCell<E> thisCell, int thisVersion, UnaryOperator<E> op) {
		this.checkVersion(thisVersion);
		this.version++;
		thisCell.applyMap(op);
	}

	public boolean isEqual(ListCell<E> thisCell,
			       int thisVersion,
			       VersionedDoubleLinkedList<?> that,
			       ListCell<?> otherCell,
			       int otherVersion) {

		this.checkVersion(thisVersion);
		that.checkVersion(otherVersion);

		return thisCell.isEqual(otherCell);
	}

	// public boolean isEqual(ListCell<E> thisCell, int thisVersion, List<?> thatList){
	// 	this.checkVersion(thisVersion);
	// 	return thisCell.isEqual(thatList);
	// }

	public String toString(ListCell<E> thisCell, int thisVersion) {
		this.checkVersion(thisVersion);
		return thisCell.toString();
	}
}
