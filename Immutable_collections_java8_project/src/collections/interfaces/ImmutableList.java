package collections.interfaces;

// http://www.scala-lang.org/api/2.11.0/index.html#scala.collection.immutable.List
// http://docs.oracle.com/javase/8/docs/api/java/util/List.html?is-external=true
// http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/collect/ImmutableList.html

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface ImmutableList<E> extends Iterable<E> {

	// size + get  + isEmpty = point de vue IndexedSeq en Scala
	// head + tail + isEmpty = point de vue LinearSeq en Scala

	/**
	 * Returns true if this list contains no elements.
	 *
	 * @returns true if this list contains no elements
	 */
	boolean isEmpty();

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

	/**
	 * Returns the first index where the element is located in the list, or -1.
	 *
	 * @param o the element to look for
	 * @return its position, or -1 if not found
	 */
	int indexOf(E elem) throws ClassCastException, NullPointerException;


	// Opérations sur les listes

	/**
	 * Returns a portion of this list.
	 *
	 * @param fromIndex low endpoint (inclusive) of the subList
	 * @param toIndex high endpoint (exclusive) of the subList
	 * @returns a portion of this list
	 * @throws IndexOutOfBoundsException if an endpoint index value is out of range (fromIndex < 0 || toIndex > size)
	 * @throws IllegalArgumentException if the endpoint indices are out of order (fromIndex > toIndex)
	 */
	ImmutableList<E> subList(int fromIndex, int toIndex) throws
	IndexOutOfBoundsException,
	IllegalArgumentException; // Java && Guava

	/**
	 * Returns a new list with the elements of this list in reverse order.
	 *
	 * @return a new list with the elements of this list in reverse order.
	 */
	ImmutableList<E> reverse(); // Guava: reverse

	/**
	 * Sorts this list using the supplied Comparator to compare elements.
	 * @param comparator
	 * @return the list 
	 */
	ImmutableList<E> sort(Comparator<? super E> comparator); // Java: sort, Scala: sorted/sortWith
	//TODO Javadoc   List<E> sort(Comparator<? super E> comparator);

	/**
	 * Returns true if this list contains the specified element. More formally,
	 * returns true if and only if this list contains at least one element e
	 * such that <code>(o==null ? e==null : o.equals(e))</code>.
	 *
	 * @param elem the element to look for
	 * @return true if it is found
	 */
	boolean contains(E elem); // Scala, Java, Guava

	/**
	 * Returns true if this list contains all of the elements of the specified collection.
	 *
	 * @param elems the elements to look for
	 * @return true if this list contains all of the elements of the specified collection
	 */
	boolean containsAll(Collection<E> elems);

	/**
	 * Returns true if this list contains all of the elements of the specified list.
	 *
	 * @param elems the elements to look for
	 * @return true if this list contains all of the elements of the specified list
	 */
	boolean containsAll(ImmutableList<E> elems);

	/**
	 * Returns true if this list contains all of the given elements
	 *
	 * @param elems the elements to look for
	 * @return true if this list contains all of the given elements
	 */
	@SuppressWarnings({"unchecked"})
	boolean containsAll(E... elems);

	/**
	 * Returns whether given predicate is satisfied by at least one element of the list.
	 *
	 * @param predicate The predicate to be tested on elements of the list.
	 * @returns true if predicate is satisfied by at least one element of the list.
	 */
	boolean any(Predicate<? super E> predicate); // Scala: exists/find

	/**
	 * Returns whether given predicate is satisfied by all elements of the list.
	 *
	 * @param predicate The predicate to be tested on elements of the list.
	 * @returns true if predicate is satified by all elements of the list.
	 */
	boolean all(Predicate<? super E> predicate); // Scala: forall

	// Factories

	/**
	 * Return a new list with the elements of this list appended to given element.
	 *
	 * @param elem The first element of the new list
	 * @return a new list with the elements of this list appended to given element.
	 */
	ImmutableList<E> cons(E elem); // Scala: + operator

	//TODO Javadocs
	// Java: addAll, Scala: ++ operator
	/**
	 * Returns a new list containing the elements from this list followed by the elements from the given list.
	 *
	 * @param elems the list to be concatened with
	 * @returns a new list containing the elements from this list followed by the elements from the given list
	 */
	ImmutableList<E> concat(Collection<E> elems);

	/**
	 * Returns a new list containing the elements from this list followed by the elements from the given list.
	 *
	 * @param elems the list to be concatened with
	 * @returns a new list containing the elements from this list followed by the elements from the given list
	 */
	ImmutableList<E> concat(ImmutableList<E> elems);

	/**
	 * Returns a new list containing the elements from this list followed by the elements from the given list.
	 *
	 * @param elems the list to be concatened with
	 * @returns a new list containing the elements from this list followed by the elements from the given list
	 */
	@SuppressWarnings({"unchecked"})
	ImmutableList<E> concat(E... elems);

	/**
	 * Returns a new list containing the elements from this list followed by the elements from the given list.
	 *
	 * @param elem the list to be concatened with
	 * @returns a new list containing the elements from this list followed by the elements from the given list
	 */
	ImmutableList<E> concat(E elem);


	//TODO Javadocs
	// Java
	ImmutableList<E> remove(Collection<E> elems);
	@SuppressWarnings({"unchecked"})


	//TODO Javadocs
	// Scala
	ImmutableList<E> union(Collection<E> elems);
	ImmutableList<E> union(ImmutableList<E> elems);
	@SuppressWarnings({"unchecked"})
	ImmutableList<E> union(E... elems);
	/**
	 * Returns a new list without all the elements of the specified elements from this list
	 * @param elems - elements to be removed from this list, if present
	 * @return  a new list without all the elements of the specified elements from this list
	 */
	ImmutableList<E> remove(ImmutableList<E> elems);
	@SuppressWarnings({"unchecked"})
	/**
	 * Returns a new list without all the element of the specified elements from this list
	 * @param elems - elements to be removed from this list, if present
	 * @return  a new list without all the element of the specified elements from this list
	 */
	ImmutableList<E> remove(E... elems);

	//TODO Javadocs
	// Scala: intersect, Java: retainsAll
	ImmutableList<E> intersect(Collection<E> elems);
	ImmutableList<E> intersect(ImmutableList<E> elems);
	@SuppressWarnings({"unchecked"})
	ImmutableList<E> intersect(E... elems);
	
	/**
	 * Returns a new list without the first occurrence of the specified element from this list
	 * @param elem - element to be removed from this list, if present
	 * @return  a new list without the first occurrence of the specified element from this list
	 */
	ImmutableList<E> remove(E elem)throws IllegalArgumentException;

	/**
	 * Returns a new list without the element at the specified position in this list
	 * @param index - the index of the element to be removed
	 * @return  a new list without the element at the specified position in this list
	 */
	ImmutableList<E> remove(int index)throws ArrayIndexOutOfBoundsException;

	/**
	 * Returns a new list consisting of the results of applying the given function to the elements of this list.
	 *
	 * @param mapper a function to apply to each element
	 * @returns the new list
	 */
	<F> ImmutableList<F> map(Function<? super E, ? extends F> mapper); // Scala: map

	/**
	 * Returns a list consisting of the elements of this list that match the given predicate.
	 *
	 * @param predicate The predicate to be tested on elements of the list.
	 * @return a list consisting of the elements of this list that match the given predicate.
	 */
	ImmutableList<E> filter(Predicate<? super E> predicate);    // Scala: filter

	// Scala: reduce

	/**
	 * Performs a reduction on the elements of this list, using an associative
	 * accumulation function, and returns an Optional describing the reduced
	 * value, if any.
	 *
	 * @param accumulator An associative function for combining two values
	 * @return an Optional describing the result of the reduction
	 * @throws NullPointerException - if the result of the reduction is null
	 */
	Optional<E> reduce(BinaryOperator<E> accumulator);

	//TODO Javadocs
	// Intégration : itérateurs + flots
	// Iterator<E> iterator();
	// Stream<E> stream();
	// Stream<E> parallelStream();

	/**
	 * Returns a sequential Stream with this collection as its source.
	 *
	 * @return a sequential Stream over the elements in this collection
	 */
	Stream<E> stream();

	/**
	 * Returns a parallel Stream with this collection as its source.
	 *
	 * @return a parallel Stream over the elements in this collection
	 */
	Stream<E> parallelStream();

	//TODO Javadocs
	// Object methods
	ImmutableList<E> clone();
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

	//TODO Javadocs
	int hashCode();

	//TODO Javadocs
	// Conversions
	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
	 * The returned array will be "safe" in that no references to it are maintained by this list. (In other words, this method must allocate a new array). The caller is thus free to modify the returned array.
	 * This method acts as bridge between array-based and collection-based APIs.
	 * 
	 * @return an array containing all of the elements in this list in proper sequence
	 */
	E[] toArray();// Scala && Java: toArray
	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array. If the list fits in the specified array, it is returned therein. Otherwise, a new array is allocated with the runtime type of the specified array and the size of this list.
	 * If the list fits in the specified array with room to spare (i.e., the array has more elements than the list), the element in the array immediately following the end of the list is set to null. (This is useful in determining the length of the list only if the caller knows that the list does not contain any null elements.)
	 * Like the toArray() method, this method acts as bridge between array-based and collection-based APIs. Further, this method allows precise control over the runtime type of the output array, and may, under certain circumstances, be used to save allocation costs.
	 * Suppose x is a list known to contain only strings. The following code can be used to dump the list into a newly allocated array of String:
     * String[] y = x.toArray(new String[0]);
	 * Note that toArray(new Object[0]) is identical in function to toArray().
	 * 
	 * @param a -the array into which the elements of the list are to be stored, if it is big enough; otherwise, a new array of the same runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 */
	<E> E[] toArray(E[] a);
	/**
	 * Returns a list containing all of the elements in this list in proper sequence
	 * 
	 * @return a list containing all of the elements in this list in proper sequence
	 */
	List<E> asList(); // Scala: toList, Guava: asList
}
