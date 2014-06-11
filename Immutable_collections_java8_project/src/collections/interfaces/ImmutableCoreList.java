package collections.interfaces;

// http://www.scala-lang.org/api/2.11.0/index.html#scala.collection.immutable.List
// http://docs.oracle.com/javase/8/docs/api/java/util/List.html?is-external=true
// http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/collect/ImmutableList.html

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface ImmutableCoreList<E> extends Iterable<E>,
Cloneable {

	public ImmutableCoreList<E> create(E[] elems);
	public <F> ImmutableCoreList<F> create(Collection<F> elems);

	/**
	 * Returns true if this list contains no elements.
	 *
	 * @returns true if this list contains no elements
	 */
	boolean isEmpty();

	/**
	 * Sorts this list using the supplied Comparator to compare elements.
	 * @param comparator
	 * @return the list
	 */
	default ImmutableCoreList<E> sort(Comparator<? super E> comparator) {
		E[] a = (E[]) toArray();
		Arrays.sort(a, comparator);
		return create(a);
	}

	/**
	 * Returns whether given predicate is satisfied by at least one element
	 * of the list.
	 *
	 * @param predicate The predicate to be tested on elements of the list.
	 * @returns true if predicate is satisfied by at least one element of
	 * the list.
	 */
	default boolean any(Predicate<? super E> predicate) {
		for (E elem : this)
			if (predicate.test(elem))
				return true;
		return false;
	}

	/**
	 * Returns whether given predicate is satisfied by all elements of the
	 * list.
	 *
	 * @param predicate The predicate to be tested on elements of the list.
	 * @returns true if predicate is satified by all elements of the list.
	 */
	default boolean all(Predicate<? super E> predicate) {
		for (E elem : this)
			if (!predicate.test(elem))
				return false;
		return true;
	}

	/**
	 * Returns the first index where the element is located in the list, or -1.
	 *
	 * @param o the element to look for
	 * @return its position, or -1 if not found
	 */
	default int indexOf(E elem) {
		int i = 0;
		for (E other : this)
			if (equals(elem, other))
				return i;
			else
				++i;
		return -1;
	}

	/**
	 * Compare two objects according to Collection semantics.
	 *
	 * @param o1 the first object
	 * @param o2 the second object
	 * @return o1 == null ? o2 == null : o1.equals(o2)
	 */
	static boolean equals(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	/**
	 * Returns true if this list contains the specified element. More formally,
	 * returns true if and only if this list contains at least one element e
	 * such that <code>(o==null ? e==null : o.equals(e))</code>.
	 *
	 * @param elem the element to look for
	 * @return true if it is found
	 */
	default boolean contains(E elem) {
		return any((E other) -> equals(elem, other));
	}

	/**
	 * Returns true if this list contains all of the elements of the
	 * specified collection.
	 *
	 * @param elems the elements to look for
	 * @return true if this list contains all of the elements of the
	 * specified collection
	 */
	default boolean containsAll(Collection<E> elems) {
		return containsAll(create(elems));
	}

	/**
	 * Returns true if this list contains all of the elements of the
	 * specified list.
	 *
	 * @param elems the elements to look for
	 * @return true if this list contains all of the elements of the
	 * specified list
	 */
	default boolean containsAll(ImmutableCoreList<E> elems) {
		return elems.all((E elem) -> contains(elem));
	}

	/**
	 * Returns true if this list contains all of the given elements
	 *
	 * @param elems the elements to look for
	 * @return true if this list contains all of the given elements
	 */
	@SuppressWarnings({"unchecked"})
	default boolean containsAll(E... elems) {
		return containsAll(create(elems));
	}

	/**
	 * Returns a new list consisting of the results of applying the given
	 * function to the elements of this list.
	 *
	 * @param mapper a function to apply to each element
	 * @returns the new list
	 */
	default <F> ImmutableCoreList<F> map(Function<? super E, ? extends F> mapper) {
		List<F> elems = new ArrayList<F>();
		for (E elem : this) {
			elems.add(mapper.apply(elem));
		}
		return create(elems);
	}

	/**
	 * Returns a list consisting of the elements of this list that match the
	 * given predicate.
	 *
	 * @param predicate The predicate to be tested on elements of the list.
	 * @return a list consisting of the elements of this list that match the
	 * given predicate.
	 */
	default ImmutableCoreList<E> filter(Predicate<? super E> predicate) {
		ArrayList<E> list = new ArrayList<E>();
		for (E elem : this)
			if (predicate.test(elem))
				list.add(elem);
		return create(list);
	}

	/**
	 * Performs a reduction on the elements of this list, using an associative
	 * accumulation function, and returns an Optional describing the reduced
	 * value, if any.
	 *
	 * @param accumulator An associative function for combining two values
	 * @return an Optional describing the result of the reduction
	 * @throws NullPointerException - if the result of the reduction is null
	 */
	default Optional<E> reduce(BinaryOperator<E> accumulator) {
		Iterator<E> it = iterator();
		if (!it.hasNext())
			return Optional.empty();

		E result = it.next();
		while (it.hasNext())
			result = accumulator.apply(result, it.next());
		return Optional.of(result);
	}

	default Spliterator<E> spliterator() {
		return Spliterators.spliteratorUnknownSize(iterator(),
				Spliterator.IMMUTABLE |
				Spliterator.ORDERED);
	}

	/**
	 * Returns a sequential Stream with this collection as its source.
	 *
	 * @return a sequential Stream over the elements in this collection
	 */
	default Stream<E> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	/**
	 * Returns a parallel Stream with this collection as its source.
	 *
	 * @return a parallel Stream over the elements in this collection
	 */
	default Stream<E> parallelStream() {
		return StreamSupport.stream(spliterator(), true);
	}

	/**
	 * Returns an array containing all of the elements in this list in
	 * proper sequence (from first to last element).
	 *
	 * The returned array will be "safe" in that no references to it are
	 * maintained by this list. (In other words, this method must allocate
	 * a new array). The caller is thus free to modify the returned array.
	 *
	 * This method acts as bridge between array-based and collection-based APIs.
	 *
	 * @return an array containing all of the elements in this list in
	 * proper sequence
	 */
	@SuppressWarnings("unchecked")
	default E[] toArray() { //TODO unused  E elem
		int size = 0; 
		for ( E elem : this)
			++size;
		return toArray((E[]) new Object[size]);
	}

	/**
	 * Returns an array containing all of the elements in this list in
	 * proper sequence (from first to last element); the runtime type of the
	 * returned array is that of the specified array. If the list fits in
	 * the specified array, it is returned therein. Otherwise, a new array
	 * is allocated with the runtime type of the specified array and the
	 * size of this list.
	 *
	 * If the list fits in the specified array with room to spare (i.e., the
	 * array has more elements than the list), the element in the array
	 * immediately following the end of the list is set to null. (This is
	 * useful in determining the length of the list only if the caller knows
	 * that the list does not contain any null elements.)
	 *
	 * Like the toArray() method, this method acts as bridge between array-
	 * based and collection-based APIs. Further, this method allows precise
	 * control over the runtime type of the output array, and may, under
	 * certain circumstances, be used to save allocation costs.
	 *
	 * Suppose x is a list known to contain only strings. The following code
	 * can be used to dump the list into a newly allocated array of String:
	 * String[] y = x.toArray(new String[0]);
	 *
	 * Note that toArray(new Object[0]) is identical in function to toArray().
	 *
	 * @param a -the array into which the elements of the list are to be
	 * stored, if it is big enough; otherwise, a new array of the same
	 * runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 */

	default E[] toArray(E[] a) {
		int i = 0;
		for (E elem : this) {
			a[i] = elem;
			++i;
		}
		return a;
	}

	/**
	 * Returns a list containing all of the elements in this list in proper
	 * sequence.
	 *
	 * @return a list containing all of the elements in this list in proper
	 * sequence
	 */
	default List<E> asList() {
		List<E> myList = new ArrayList<E>();
		for (E e : this){
			myList.add(e);
		}
		return myList;
	}

	public ImmutableCoreList<E> clone();

	// To be used for the clone() method in concrete classes (since Object
	// methods cannot be overriden with the default keyword)
	static <E> ImmutableCoreList<E> clone(ImmutableCoreList<E> list) {
		return list.create(list.asList());
	}

	/**
	 * Compares the specified object with this list for equality. Returns true
	 * if and only if the specified object is also a list, both lists have the
	 * same size, and all corresponding pairs of elements in the two lists are
	 * equal. (Two elements e1 and e2 are equal if
	 * <code>(e1==null ? e2==null : e1.equals(e2)).)</code> In other words, two
	 * lists are defined to be equal if they contain the same elements in the
	 * same order. This definition ensures that the equals method works properly
	 * across different implementations of the ImmutableList interface.
	 *
	 * @param o the object to be compared for equality with this list
	 * @return if the specified object is equal to this list
	 */
	boolean equals(Object o);

	// To be used for the equals() method in concrete classes (since Object
	// methods cannot be overriden with the default keyword)
	static <E> boolean equals(ImmutableCoreList<E> list, Object o) {
		if (! (o instanceof ImmutableCoreList))
			return false;

		@SuppressWarnings("rawtypes")
		ImmutableCoreList other = (ImmutableCoreList) o;

		Iterator<E> it1 = list.iterator();
		@SuppressWarnings("rawtypes") Iterator it2 = other.iterator();

		while (it1.hasNext() && it2.hasNext()) {
			if (!equals(it1.next(), it2.next()))
				return false;
		}
		if (it1.hasNext() || it2.hasNext())
			return false;

		return true;
	}

	/**
	 * Hash an object.
	 *
	 * @param o the object to hash
	 * @return o1 == null ? 0 : o1.hashCode()
	 */
	static int hashCode(Object o) {
		return o == null ? 0 : o.hashCode();
	}

	public int hashCode();

	// To be used for the hashCode() method in concrete classes (since Object
	// methods cannot be overriden with the default keyword)
	static <E> int hashCode(ImmutableCoreList<E> list) {
		int hashCode = 1;
		Iterator<E> itr = list.iterator();
		while (itr.hasNext())
			hashCode = 31 * hashCode + hashCode(itr.next());
		return hashCode;
	}
}
