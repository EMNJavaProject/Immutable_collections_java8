package collections.interfaces;

// http://www.scala-lang.org/api/2.11.0/index.html#scala.collection.immutable.List
// http://docs.oracle.com/javase/8/docs/api/java/util/List.html?is-external=true
// http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/collect/ImmutableList.html

import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.Optional;
import java.util.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.function.BinaryOperator;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Function;

public interface ImmutableList<E> extends Iterable<E> {

    // size + get + isEmpty = point de vue IndexedSeq en Scala

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
    int indexOf(E elem);

    // head + tail + isEmpty = point de vue LinearSeq en Scala

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
    E last() throws NoSuchElementException;;

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

    // List<E> sort(Comparator<? super E> comparator); // Java: sort, Scala: sorted/sortWith

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
    @SuppressWarnings({"unchecked", "varags"})
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

    // Java: addAll, Scala: ++ operator
    // ImmutableList<E> concat(Collection<E> elems);
    // ImmutableList<E> concat(ImmutableList<E> elems);
    // @SuppressWarnings({"unchecked", "varags"})
    // ImmutableList<E> concat(E... elems);

    /**
     * Returns a new list containing the elements from this list followed by the elements from the given list.
     *
     * @param elem the list to be concatened with
     * @returns a new list containing the elements from this list followed by the elements from the given list
     */
    ImmutableList<E> concat(E elem);

    // Java
    // ImmutableList<E> remove(Collection<E> elems);
    // ImmutableList<E> remove(ImmutableList<E> elems);
    // @SuppressWarnings({"unchecked", "varags"})
    // ImmutableList<E> remove(E... elems);
    // ImmutableList<E> remove(E elem);
    // ImmutableList<E> remove(int index);

    // Scala
    // ImmutableList<E> union(Collection<E> elems);
    // ImmutableList<E> union(ImmutableList<E> elems);
    // @SuppressWarnings({"unchecked", "varags"})
    // ImmutableList<E> union(E... elems);

    // Scala: intersect, Java: retainsAll
    // ImmutableList<E> intersect(Collection<E> elems);
    // ImmutableList<E> intersect(ImmutableList<E> elems);
    // @SuppressWarnings({"unchecked", "varags"})
    // ImmutableList<E> intersect(E... elems);

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
    // Optional<E> reduce(BinaryOperator<E> accumulator);
    // E reduce(E identity, BinaryOperator<E> accumulator);
    // <F> F reduce(F identity, BiFunction<F, ? super E, F> accumulator, BinaryOperator<F> combiner);

    // Intégration : itérateurs + flots
    // Iterator<E> iterator();
    // Stream<E> stream();
    // Stream<E> parallelStream();

    // Object methods

    // ImmutableList<E> clone();

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

    // int hashCode();

    // Conversions
    // E[] toArray();    // Scala && Java: toArray
    // List<E> asList(); // Scala: toList, Guava: asList
}
