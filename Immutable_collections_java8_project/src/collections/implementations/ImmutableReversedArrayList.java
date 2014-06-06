package collections.implementations;

import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ImmutableReversedArrayList<E> extends ImmutableArrayList<E> {

	/** The encapsulated list we delegate things to. */
	private final ImmutableArrayList<E> list;

	/**
	 * Construct a reverse view of the given list.
	 *
	 * @param list the list to be reversed.
	 */
	public ImmutableReversedArrayList(ImmutableArrayList<E> list) {
		this.list = list;
	}

	public int size() {
		return list.size();
	}

	/**
	 * Return equivalent of index counting from the end to simulate the
	 * reversed view of the list.
	 *
	 * @param index The index to be reversed
	 **/
	private int reverseIndex(int index) {
		return size() - 1 - index;
	}

	public E get(int index) throws IndexOutOfBoundsException {
		return list.get(reverseIndex(index));
	}

	public int indexOf(E elem) {
		int index = list.indexOf(elem);
		if (index != -1)
			index = reverseIndex(index);
		return index;
	}

	public E head() throws NoSuchElementException {
		return list.last();
	}

	public E last() throws NoSuchElementException {
			return list.head();
	}

	@SuppressWarnings("unchecked")
	public ImmutableArrayList<E> subList(int fromIndex, int toIndex) throws
		IndexOutOfBoundsException,
		IllegalArgumentException {
		if (fromIndex == toIndex)
			return new ImmutableArrayList<E>();

		return list.subList(reverseIndex(toIndex  -1),
				    reverseIndex(fromIndex-1)).reverse();
	}

	public ImmutableArrayList<E> reverse() {
		return list;
	}

	@SuppressWarnings("unchecked")
	public ImmutableArrayList<E> remove(int index) {
		return list.remove(reverseIndex(index)).reverse();
	}

	class ReversedListIterator implements Iterator<E> {

		/** Current index pointed by the iterator */
		private int index;

		/**
		 * Create a new iterator starting from the beginning of the list.
		 */
		public ReversedListIterator() {
			index = size()-1;
		}

		public boolean hasNext() {
			return index >= 0 ? true : false;
		}

		public E next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException();

			E elem = list.get(index);
			--index;
			return elem;
		}

		public void remove() throws
		UnsupportedOperationException,
		IllegalStateException {
			throw new UnsupportedOperationException();
		}
	}

	public Iterator<E> iterator() {
		return new ReversedListIterator();
	}

	@SuppressWarnings("unchecked")
	public E[] toArray() {
		List<E> tmp = list.asList();
		Collections.reverse(tmp);
		return (E[]) tmp.toArray();
	}
}
