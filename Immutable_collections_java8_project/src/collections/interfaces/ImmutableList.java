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

public interface ImmutableList<E> /* extends Iterable<E> */ {

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
    // int indexOf(E elem);

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
    // E last();

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

    // List<E> reverse();         		 // Guava: reverse
    // List<E> sort(Comparator<? super E> comparator); // Java: sort, Scala: sorted/sortWith

    // Scala, Java, Guava
    // boolean contains(E elem);
    // Java
    // boolean containsAll(Collection<E> elems);
    // boolean containsAll(ImmutableList<E> elems);

    // @SuppressWarnings({"unchecked", "varags"})
    // boolean containsAll(E... elems);

    // boolean any(Predicate<? super E> predicate); // Scala: exists/find
    // boolean all(Predicate<? super E> predicate); // Scala: forall

    // Fabriques (ajout d'un élément en tête)

    // Scala: + operator
    // ImmutableList<E> cons(E elem);

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

    // Scala: map
    // <F> ImmutableList<F> map(Function<? super E, ? super F> mapper);
    // Scala: filter
    // <F> ImmutableList<E> filter(Predicate<? super E> predicate);
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
    // boolean equals(Object o);
    // int hashCode();

    // Conversions
    // E[] toArray();    // Scala && Java: toArray
    // List<E> asList(); // Scala: toList, Guava: asList
}
