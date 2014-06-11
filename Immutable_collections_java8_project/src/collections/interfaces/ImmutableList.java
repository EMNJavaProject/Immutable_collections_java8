package collections.interfaces;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public interface ImmutableList<E> extends InductiveList<E>, IterativeList<E> {

	public ImmutableList<E> create(E[] elems);

	/**
	 * Returns a portion of this list.
	 *
	 * @param fromIndex low endpoint (inclusive) of the subList
	 * @param toIndex high endpoint (exclusive) of the subList
	 * @returns a portion of this list
	 * @throws IndexOutOfBoundsException if an endpoint index value is out
	 * of range (fromIndex < 0 || toIndex > size)
	 * @throws IllegalArgumentException if the endpoint indices are out of
	 * order (fromIndex > toIndex)
	 */
	ImmutableList<E> subList(int fromIndex, int toIndex) throws
	IndexOutOfBoundsException,
	IllegalArgumentException;

	/**
	 * Returns a new list with the elements of this list in reverse order.
	 *
	 * @return a new list with the elements of this list in reverse order.
	 */
	default ImmutableList<E> reverse() {
		if (isEmpty())
			return this;
		else
			return tail().reverse().concat(head());
	}

	/**
	 * Returns a new list containing the elements from this list followed by
	 * the elements from the given list.
	 *
	 * @param elems the list to be concatened with
	 * @returns a new list containing the elements from this list followed
	 * by the elements from the given list
	 */
	default ImmutableList<E> concat(Collection<E> elems) {
		ImmutableList<E> list = this;
		for (E elem : elems){
			list = list.concat(elem);
		}
		return list;
	}

	/**
	 * Returns a new list containing the elements from this list followed by
	 * the elements from the given list.
	 *
	 * @param elems the list to be concatened with
	 * @returns a new list containing the elements from this list followed
	 * by the elements from the given list
	 */
	default ImmutableList<E> concat(ImmutableList<E> elems) {
		ImmutableList<E> list = this;
		for (E elem : elems){
			list = list.concat(elem);
		}
		return list;
	}

	/**
	 * Returns a new list containing the elements from this list followed by
	 * the elements from the given list.
	 *
	 * @param elems the list to be concatened with
	 * @returns a new list containing the elements from this list followed
	 * by the elements from the given list
	 */
	@SuppressWarnings({"unchecked"})
	default ImmutableList<E> concat(E... elems) {
		ImmutableList<E> list = this;
		for (int i = 0 ; i <elems.length ; ++i){
			list = list.concat(elems[i]);
		}
		return list;
	}

	/**
	 * Returns a new list containing the elements from this list followed by
	 * the elements from the given list.
	 *
	 * @param elem the list to be concatened with
	 * @returns a new list containing the elements from this list followed
	 * by the elements from the given list
	 */
	default ImmutableList<E> concat(E elem) {
		@SuppressWarnings("unchecked")
		E[] elems = (E[]) new Object[size() + 1];
		int i = 0;
		for (E e : this) {
			elems[i] = e;
			++i;
		}
		elems[size()] = elem;
		return create(elems);
	}

	/**
	 * Returns a new list without all the elements of the specified
	 * elements from this list.
	 *
	 * @param elems - elements to be removed from this list, if present
	 * @return  a new list without all the elements of the specified
	 * elements from this list
	 */
	default ImmutableList<E> remove(ImmutableList<E> elems) {
		ImmutableList<E> list = this;
		for (E elem : elems) {
			list = list.remove(elem);
		}
		return list;
	}

	/**
	 * Returns a new list without all the elements of the specified
	 * elements from this list.
	 *
	 * @param elems - elements to be removed from this list, if present
	 * @return  a new list without all the elements of the specified
	 * elements from this list
	 */
	default ImmutableList<E> remove(Collection<E> elems) {
		ImmutableList<E> list = this;
		for (E elem : elems) {
			list = list.remove(elem);
		}
		return list;
	}

	/**
	 * Returns a new list without all the element of the specified elements
	 * from this list
	 *
	 * @param elems - elements to be removed from this list, if present
	 * @return  a new list without all the element of the specified elements
	 * from this list
	 */
	@SuppressWarnings({"unchecked"})
	default ImmutableList<E> remove(E... elems) {
		ImmutableList<E> list = this;
		for (int i = 0 ; i <elems.length ; ++i){
			list = list.remove(elems[i]);
		}
		return list;
	}

	/**
	 * Returns a new list without the first occurrence of the specified
	 * element from this list.
	 *
	 * @param elem - element to be removed from this list, if present
	 * @return  a new list without the first occurrence of the specified
	 * element from this list
	 */
	@SuppressWarnings("unchecked")
	default ImmutableList<E> remove(E elem) throws IllegalArgumentException {
		E[] newElems;
		int i;
		boolean remove;
		newElems = (E[]) new Object[size() - 1];
		i = 0;
		remove = false;
		for (E e : this) {
			if(!remove && ImmutableCoreList.equals(elem, e))
				remove = true;
			else{
				if (i == this.size()-1)
					throw new IllegalArgumentException();
				newElems[i] = e;
				i++;
			}
		}

		return create(newElems);
	}

	/**
	 * Returns a new list without the element at the specified position in
	 * this list.
	 *
	 * @param index - the index of the element to be removed
	 * @return  a new list without the element at the specified position in
	 * this list
	 */
	ImmutableList<E> remove(int index) throws ArrayIndexOutOfBoundsException;

	// To be used for the clone() method in concrete classes (since Object
	// methods cannot be overriden with the default keyword)
	static <E> ImmutableList<E> clone(ImmutableList<E> list) {
		return list.subList(0, list.size());
	}

	default ImmutableList<E> tail() throws UnsupportedOperationException {
		if (isEmpty())
			throw new UnsupportedOperationException();
		else
			return subList(1, size());
	}


	default E head() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return get(0);
	}

	default E last() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return get(size()-1);
	}

	@SuppressWarnings("unchecked")
	default <F> ImmutableList<F> map(Function<? super E, ? extends F> mapper) {
		return (ImmutableList<F>) InductiveList.super.map(mapper);
	}

	default ImmutableList<E> filter(Predicate<? super E> predicate) {
		return (ImmutableList<E>) InductiveList.super.filter(predicate);
	}

	ImmutableList<E> cons(E elem);
}
