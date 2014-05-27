package collections.interfaces;

public interface IterativeList<E> {

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @returns the element at the specified position in this list
	 * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())
	 */
	E get(int index) throws IndexOutOfBoundsException;

	/**
	 * Returns the first index where the element is located in the list, or -1.
	 *
	 * @param o the element to look for
	 * @return its position, or -1 if not found
	 */
	int indexOf(E elem);

}
