package collections.interfaces;

import java.util.Spliterators;
import java.util.Spliterator;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IterativeList<E> extends ImmutableCoreList<E> {

	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of elements in this list.
	 *
	 * @returns the number of elements in this list
	 **/
	int size();

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @returns the element at the specified position in this list
	 * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())
	 */
	E get(int index) throws IndexOutOfBoundsException;

	default Spliterator<E> spliterator() {
		return Spliterators.spliterator(iterator(),
				size(),
				Spliterator.IMMUTABLE |
				Spliterator.ORDERED   |
				Spliterator.SIZED     |
				Spliterator.SUBSIZED);
	}

	public boolean equals(Object o);

	// To be used for the equals() method in concrete classes (since Object
	// methods cannot be overridden with the default keyword)
	@SuppressWarnings("unchecked")
	static <E> boolean equals(IterativeList<E> list, Object o) {
		if (o instanceof IterativeList)
			return list.equals((IterativeList<E>)o);
		else if (o instanceof ImmutableCoreList) 
			return ImmutableCoreList.equals(list, o);
		else
			return false;
	}

	default public boolean equals(IterativeList<E> other) {
		if (size() != other.size())
			return false;

		Iterator<E> it1 = iterator();
		@SuppressWarnings("rawtypes")
		Iterator it2 = other.iterator();

		while (it1.hasNext()) {
			if (!ImmutableCoreList.equals(it1.next(), it2.next())) //TODO
				return false;
		}

		return true;
	}

	@SuppressWarnings({ "unchecked"})
	default E[] toArray() {
		return toArray((E[]) new Object[size()]);
	}

	@SuppressWarnings("unchecked")
	default <F> IterativeList<F> map(Function<? super E, ? extends F> mapper) {
		return (IterativeList<F>) ImmutableCoreList.super.map(mapper);
	}

	default IterativeList<E> filter(Predicate<? super E> predicate) {
		return (IterativeList<E>) ImmutableCoreList.super.filter(predicate);
	}
}
