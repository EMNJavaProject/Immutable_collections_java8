package collections.interfaces;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public interface InductiveIterativeList<E> extends InductiveList<E>,
						   IterativeList<E> {

	public InductiveIterativeList<E> create(E[] elems);

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
	InductiveIterativeList<E> subList(int fromIndex, int toIndex) throws
		IndexOutOfBoundsException,
		IllegalArgumentException;

	/**
	 * Returns a new list with the elements of this list in reverse order.
	 *
	 * @return a new list with the elements of this list in reverse order.
	 */
	default InductiveIterativeList<E> reverse() {
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
	default InductiveIterativeList<E> concat(Collection<E> elems) {
		InductiveIterativeList<E> list = this;
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
	default InductiveIterativeList<E> concat(InductiveIterativeList<E> elems) {
		InductiveIterativeList<E> list = this;
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
        default InductiveIterativeList<E> concat(E... elems) {
		InductiveIterativeList<E> list = this;
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
         default InductiveIterativeList<E> concat(E elem) {
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
	default InductiveIterativeList<E> remove(InductiveIterativeList<E> elems) {
		InductiveIterativeList<E> list = this;
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
	default InductiveIterativeList<E> remove(Collection<E> elems) {
		InductiveIterativeList<E> list = this;
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
	default InductiveIterativeList<E> remove(E... elems) {
		InductiveIterativeList<E> list = this;
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
        default InductiveIterativeList<E> remove(E elem) throws IllegalArgumentException {
		E[] newElems;
		int i;
		boolean remove;
		newElems = (E[]) new Object[size() - 1];
		i = 0;
		remove = false;
		for (E e : this) {
			if(!remove && ImmutableList.equals(elem, e))
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
	InductiveIterativeList<E> remove(int index) throws ArrayIndexOutOfBoundsException;

	// To be used for the clone() method in concrete classes (since Object
	// methods cannot be overriden with the default keyword)
	static <E> InductiveIterativeList<E> clone(InductiveIterativeList<E> list) {
		return list.subList(0, list.size());
	}

	default InductiveIterativeList<E> tail() throws UnsupportedOperationException {
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

        default <F> InductiveIterativeList<F> map(Function<? super E, ? extends F> mapper) {
		return (InductiveIterativeList<F>) InductiveList.super.map(mapper);
	}

        default InductiveIterativeList<E> filter(Predicate<? super E> predicate) {
		return (InductiveIterativeList<E>) InductiveList.super.filter(predicate);
	}

	InductiveIterativeList<E> cons(E elem);
}
