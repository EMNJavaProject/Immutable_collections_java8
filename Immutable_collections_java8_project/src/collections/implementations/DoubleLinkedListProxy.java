package collections.implementations;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class DoubleLinkedListProxy<E> /* implements List<E> */ {

	/** The list we keep track of */
	private VersionedDoubleLinkedList<E> versionedList;

	/** The first cell of the list */
	private ListCell<E> cell;

	/** Version of the list this proxy should be wrapping */
	private int version;

	public DoubleLinkedListProxy() {
		this(new VersionedDoubleLinkedList<E>());
	}

	public DoubleLinkedListProxy(VersionedDoubleLinkedList<E> versionedList) {
		this.versionedList = versionedList;
		this.cell          = versionedList.getFirstCell();
		this.version       = versionedList.getVersion();
	}

	public DoubleLinkedListProxy(VersionedDoubleLinkedList<E> versionedList,
				     ListCell<E> cell) {
		this.versionedList = versionedList;
		this.cell          = cell;
		this.version       = versionedList.getVersion();
	}

	public DoubleLinkedListProxy<E> nil() { // List
		return new DoubleLinkedListProxy<E>();
	}

	public boolean isEmpty() {
		return versionedList.isEmpty(cell, version);
	}

	public DoubleLinkedListProxy<E> cons(E head) { // List
		this.versionedList.addFirst(head, cell, version);
		return new DoubleLinkedListProxy<E>(versionedList, cell.getPrevious());
	}

	public DoubleLinkedListProxy<E> tail() { // List
		ListCell<E> tailCell = versionedList.tail(cell, version);
		return new DoubleLinkedListProxy<E>(versionedList, tailCell);
	}

	public E head() {
		return this.versionedList.head(this.cell, this.version);
	}

	public DoubleLinkedListProxy<E> concat(DoubleLinkedListProxy<E> that) { // List list
		if (!(that instanceof DoubleLinkedListProxy<?>))
			throw new UnsupportedOperationException();

		DoubleLinkedListProxy<E> thatList = (DoubleLinkedListProxy<E>) that;
		ListCell<E> newCell = versionedList.concatenate(cell,
								version,
								thatList.versionedList,
								thatList.cell,
								thatList.version);
		return new DoubleLinkedListProxy<E>(versionedList, newCell);
	}

	public DoubleLinkedListProxy<E> reverse() { // List
		this.versionedList.reverse(cell, version);
		return new DoubleLinkedListProxy<E>(versionedList, cell);
	}

	public <R> DoubleLinkedListProxy<R> map(Function<E, R> fctn) { // List
		VersionedDoubleLinkedList<R> newVersionedList = versionedList.map(cell, version, fctn);
		return new DoubleLinkedListProxy<R>(newVersionedList, newVersionedList.getFirstCell());
	}

	public DoubleLinkedListProxy<E> endoMap(UnaryOperator<E> op) { // List
		versionedList.applyMap(cell, version, op);
		return new DoubleLinkedListProxy<E>(versionedList, cell);
	}

	public String toString() {
		return this.versionedList.toString(cell, version);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof DoubleLinkedListProxy<?>)) { // List
			return false;
		}
		if (obj instanceof DoubleLinkedListProxy<?>) {
			DoubleLinkedListProxy<?> thatList = (DoubleLinkedListProxy<?>) obj;
			return versionedList.isEqual(cell,
						     version,
						     thatList.versionedList,
						     thatList.cell,
						     thatList.version);
		}
		// else {
			// List<?> thatList = (List<?>) obj; // List list
			// return versionedList.isEqual(cell, version, thatList);
		// }
		return false; // to suppr
	}
}
