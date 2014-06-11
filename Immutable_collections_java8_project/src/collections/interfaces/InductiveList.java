package collections.interfaces;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public interface InductiveList<E> extends ImmutableCoreList<E> { //TODO ERROR : No isEmpty() method.

	/**
	 * Return a new list with the elements of this list appended to given element.
	 *
	 * @param elem The first element of the new list
	 * @return a new list with the elements of this list appended to given element.
	 */
	InductiveList<E> cons(E elem);

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
	InductiveList<E> tail() throws UnsupportedOperationException;

	/**
	 * Returns the last element of the list.
	 *
	 * @returns the last element in the list
	 * @throws NoSuchElementException if the list is empty
	 */
	E last() throws NoSuchElementException;

	@SuppressWarnings("unchecked")
	default <F> InductiveList<F> map(Function<? super E, ? extends F> mapper) {
		return (InductiveList<F>) ImmutableCoreList.super.map(mapper);
	}

	default InductiveList<E> filter(Predicate<? super E> predicate) {
		return (InductiveList<E>) ImmutableCoreList.super.filter(predicate);
	}
}
