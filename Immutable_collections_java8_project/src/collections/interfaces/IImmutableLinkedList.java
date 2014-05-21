package collections.interfaces;

import java.util.NoSuchElementException;

public interface IImmutableLinkedList<E> extends ImmutableList<E>{

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
    IImmutableLinkedList<E> tail() throws UnsupportedOperationException;

    /**
     * Returns the last element of the list.
     *
     * @returns the last element in the list
     * @throws NoSuchElementException if the list is empty
     */
    E last() throws NoSuchElementException;
    
    
    /**
     * Returns a new list containing the elements from this list followed by the elements from the given list.
     *
     * @param elem the list to be concatened with
     * @returns a new list containing the elements from this list followed by the elements from the given list
     */
    IImmutableLinkedList<E> add(E elem);
}
