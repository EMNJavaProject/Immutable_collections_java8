package collections.interfaces;

import java.util.NoSuchElementException;

public interface InductiveList<E> {

	/**
	 * Return a new list with the elements of this list appended to given element.
	 *
	 * @param elem The first element of the new list
	 * @return a new list with the elements of this list appended to given element.
	 */
	ImmutableList<E> cons(E elem); // Scala: + operator

	/**
	 * Returns the first element in the list.
	 *
	 * @returns the first element in the list.
	 * @throws NoSuchElementException if the list is empty
	 */
	E head() throws NoSuchElementException;

	/**
	 * Returns a list with all elements of this list except the first one.
	 *
	 * @returns a list with all elements of this list except the first one
	 * @throws UnsupportedOperationException if this list is empty
	 */
	ImmutableList<E> tail() throws UnsupportedOperationException;

	/**
	 * Returns the last element of the list.
	 *
	 * @returns the last element in the list
	 * @throws NoSuchElementException if the list is empty
	 */
	E last() throws NoSuchElementException;
}
