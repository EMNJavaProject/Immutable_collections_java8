package collections.implementations;

import java.util.function.Function;
import java.util.function.UnaryOperator;

// import collections.interfaces.InductiveIterativeList;
// import collections.interfaces.IterativeList;
// import collections.interfaces.ImmutableList;

public abstract class ListCell<E> {

	/** The cell before this one in the linked list */
	protected ListCell<E> previous;

	/** The cell after this one in the linked list */
	protected ListCell<E> next;

	final public ListCell<E> getPrevious() {
		return previous;
	}

	final public void setPrevious(ListCell<E> l) {
		this.previous = l;
	}

	public ListCell<E> getNext() {
		return this.next;
	}

	final public void setNext(ListCell<E> l) {
		this.next = l;
	}

	final public boolean isFirst() {
		return this.getPrevious().isEmpty();
	}

	abstract public boolean isEmpty();
	abstract public E       getItem();
	abstract public void    setItem(E item);

	abstract public void concat(ListCell<E> cell);
	abstract public void reverse(ListCell<E> nilCell);
	abstract public ListCell<E> clone();
	abstract public <R> ListCell<R> map(Function<E, R> mapper);
	abstract public void applyMap(UnaryOperator<E> mapper);

	abstract public boolean isEqual(ListCell<?> cell);
	// abstract public boolean isEqual(List<?> list);

	static <E> void linkCells(ListCell<E> first, ListCell<E> second) {
		first.next = second;
		second.previous = first;
	}
}

final class NilCell<E> extends ListCell<E> {

	public NilCell() {
		this.previous = this;
		this.next     = this;
	}

	public boolean isEmpty() {
		return true;
	}

	public E getItem() {
		throw new UnsupportedOperationException();
	}

	public void setItem(E item) {
		throw new UnsupportedOperationException();
	}

	public void concat(ListCell<E> cell) {
		throw new UnsupportedOperationException();
	}

	public void reverse(ListCell<E> nilCell) { }

	public void applyMap(UnaryOperator<E> mapper) { }

	public <F> ListCell<F> map(Function<E, F> mapper) {
		return new NilCell<F>();
	}

	public boolean isEqual(ListCell<?> cell) {
		return cell.isEmpty();
	}

	// public boolean isEqual(List<?> list) {
	// 	return cell.isEmpty();
	// }

	public NilCell<E> clone() {
		return new NilCell<E>();
	}

	public String toString() {
		return "[]";
	}
}

class ConsCell<E> extends ListCell<E> {

	/** The element of this cell */
	private E item;

	public ConsCell(E head, ListCell<E> tail) {
		setItem(head);
		this.next = tail;
		this.previous = tail.previous;
		tail.previous = this;
		this.previous.next = this;
	}

	private ConsCell(E item) {
		this.item = item;
	}

	public boolean isEmpty() {
		return false;
	}

	public E getItem() {
		return item;
	}

	public void setItem(E item) {
		this.item = item;
	}

	// TODO: do not copy & ++version
	public void concat(ListCell<E> _cell) {
		ListCell<E> cell = _cell.clone();
		ListCell<E> lastButOne = this.previous.previous;
		this.previous = cell.previous;
		linkCells(lastButOne, cell);
	}

	// ++version
	public void reverse(ListCell<E> nilCell) {
		ListCell<E> rightCell = nilCell.previous;
		ListCell<E> leftCell  = this;

		while (leftCell != rightCell) {
			E rightItem = rightCell.getItem();
			rightCell.setItem(leftCell.getItem());
			leftCell.setItem(rightItem);

			if(leftCell.next == rightCell) {
				break;
			}

			leftCell  = leftCell.next;
			rightCell = rightCell.previous;
		}
	}

	public ListCell<E> clone() {
		ListCell<E> cell = this;
		ListCell<E> result = new ConsCell<>(cell.getItem());
		ListCell<E> newCell = result;
		cell = cell.getNext();

		while (!cell.isEmpty()) {
			ListCell<E> newNextCell = new ConsCell<>(cell.getItem());
			linkCells(newCell, newNextCell);

			newCell = newNextCell;
			cell    = cell.getNext();
		}

		ListCell<E> newNilCell = cell.clone();
		linkCells(newNilCell, result);
		linkCells(newCell, newNilCell);

		return result;
	}

	public <F> ListCell<F> map(Function<E, F> mapper) {
		ListCell<E> cell = this;
		ListCell<F> result = new ConsCell<>(mapper.apply(cell.getItem()));
		ListCell<F> newCell = result;
		cell = cell.getNext();

		while (!cell.isEmpty()) {
			ListCell<F> newNextCell = new ConsCell<>(mapper.apply(cell.getItem()));
			linkCells(newCell, newNextCell);
			newCell = newNextCell;
			cell = cell.getNext();
		}

		ListCell<F> newNilCell = cell.map(mapper);
		linkCells(newNilCell, result);
		linkCells(newCell, newNilCell);

		return result;
	}

	// ++version
	public void applyMap(UnaryOperator<E> mapper) {
		ListCell<E> cell = this;
		cell.setItem(mapper.apply(cell.getItem()));
		cell = cell.getNext();
		while (!cell.isEmpty()) {
			cell.setItem(mapper.apply(cell.getItem()));
			cell = cell.getNext();
		}
	}

	public boolean isEqual(ListCell<?> cell) {
		if(cell.isEmpty())
			return false;
		if (!this.getItem().equals(cell.getItem()))
			return false;

		ListCell<E> thisCell = this.getNext();
		cell = cell.getNext();
		while (!thisCell.isEmpty()) {

			if (cell.isEmpty())
				return false;
			if(!thisCell.getItem().equals(cell.getItem()))
				return false;

			thisCell = thisCell.getNext();
			cell = cell.getNext();
		}
		return thisCell.isEqual(cell);
	}

	// public boolean isEqual(List<?> list) {
	// 	if (list.isEmpty())
	// 		return false;
	// 	if (!this.getItem().equals(list.head()))
	// 		return false;

	// 	ListCell<E> thisCell = this.getNext();
	// 	list = list.tail();
	// 	while (!thisCell.isEmpty()){

	// 		if(list.isEmpty())
	// 			return false;
	// 		if(!thisCell.getItem().equals(list.head()))
	// 			return false;

	// 		thisCell = thisCell.getNext();
	// 		list = list.tail();
	// 	}
	// 	return thisCell.isEqual(list);
	// }

	public String toString(){
		String result = "[" + this.getItem();
		ListCell<E> thisCell = this.getNext();
		String sep = ", ";
		while (!thisCell.isEmpty()) {
			result = result + sep + thisCell.getItem();
			thisCell = thisCell.getNext();
		}
		return result + "]";
	}
}
