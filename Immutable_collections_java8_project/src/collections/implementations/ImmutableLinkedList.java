package collections.implementations;

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

import collections.interfaces.ImmutableList;

class Node<E> {

        /** The element in the list */
        private final E element;

        /** The next list node, null if this is last */
	private final Node<E> next;

	public Node(E element, Node<E> next) {
		this.element=element;
		this.next=next;
	}

	public Node(E element) {
		this(element, null);
	}

	/**
	 * Returns the element in the node.
	 *
	 * @returns the element in the node.
	 */
	public E getElement() {
		return this.element;
	}

        /** Returns the next list node, null if this is last.
	 *
	 * @returns the next list node, null if this is last
	 */
	public Node<E> getNext() {
		return next;
	}

        /** Returns whether this node is followed by another one.
	 *
	 * @returns true if this node is followed by another one.
	 */
	public boolean hasNext() {
		return getNext() != null;
	}
}

public class ImmutableLinkedList<E> implements ImmutableList<E> {

        /** The first node element of the list. */
	private final Node<E> head;

	/** The number of elements in this list. */
	private final int size;

	/**
	 * Returns the first node element of the list.
	 *
	 * @returns the first node element of the list.
	 **/
	private Node<E> headNode() {
	    return head;
	}

	public int size() {
	    return size;
	}

	// Constructors

	/**
	 * Create a empty linked list.
	 */
	public ImmutableLinkedList() {
	    this.head = null;
	    this.size = 0;
	}

	// public ImmutableLinkedList<E>(Collection<E> elems);

	/**
	 * Create a linked list containing the given elements in order.
	 *
	 * @param elems the elements to populate this list from
	 * @throws NullPointerException if elems is null
	 */
	@SuppressWarnings({"unchecked", "varags"})
	public ImmutableLinkedList(E... elems) {
	    if (elems == null)
		throw new NullPointerException();

	    Node<E> head = null;
	    for (int i = elems.length-1 ; i >= 0 ; --i)
		head = new Node<E>(elems[i], head);

	    this.head = head;
	    this.size = elems.length;
	}

        // Operations

        public boolean isEmpty() {
	    return size() == 0;
	}

	public E get(int index) throws IndexOutOfBoundsException {
	    if (head == null)
		throw new IndexOutOfBoundsException();

	    Node<E> node = headNode();
	    int i = 0;
	    while (node != null) {
		if (i == index)
		    return node.getElement();
		else {
		    if (!node.hasNext())
			throw new IndexOutOfBoundsException();
		    else {
			node = node.getNext();
			++i;
		    }
		}
	    }
	    return node.getElement();
	}

    // public int indexOf(E elem);

	public E head() throws NoSuchElementException {
	    if (isEmpty())
		throw new NoSuchElementException();
	    else
		return headNode().getElement();
	}

	@SuppressWarnings("unchecked")
	public ImmutableList<E> tail() throws UnsupportedOperationException {
	    if (isEmpty())
		throw new UnsupportedOperationException();
	    else
		return subList(1, size());
	}

    // public E last();

	@SuppressWarnings("unchecked")
	public ImmutableList<E> subList(int fromIndex, int toIndex) throws
	    IndexOutOfBoundsException,
	    IllegalArgumentException {

	    if (fromIndex < 0 || toIndex > size())
		throw new IndexOutOfBoundsException();
	    if (fromIndex > toIndex)
		throw new IllegalArgumentException();
	    if (fromIndex == toIndex)
		return new ImmutableLinkedList<E>();

	    int i = 0;
	    Node node = headNode();
	    while (i != fromIndex) {
		node = node.getNext();
		++i;
	    }

	    E[] elems = (E[]) new Object[toIndex - fromIndex];
	    while (i != toIndex) {
		elems[i - fromIndex] = (E)node.getElement();
		node = node.getNext();
		++i;
	    }

	    return new ImmutableLinkedList<E>(elems);
	}

    // public List<E> reverse();
    // public List<E> sort(Comparator<? super E> comparator);

    // public boolean contains(E elem);
    // public boolean containsAll(Collection<E> elems);
    // public boolean containsAll(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public boolean containsAll(E... elems);

    // public boolean any(Predicate<? super E> predicate);
    // public boolean all(Predicate<? super E> predicate);

    // Factories

    // public ImmutableList<E> cons(E elem);

    // public ImmutableList<E> concat(Collection<E> elems);
    // public ImmutableList<E> concat(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public ImmutableList<E> concat(E... elems);

	@SuppressWarnings("unchecked")
	public ImmutableList<E> concat(E elem) {
	    E[] elems = (E[]) new Object[size() + 1];
	    Node node = headNode();
	    for (int i = 0 ; i < size() ; ++i) {
		elems[i] = (E)node.getElement();
		node = node.getNext();
	    }
	    elems[size()] = elem;
	    return new ImmutableLinkedList<E>(elems);
	}

    // public ImmutableList<E> remove(Collection<E> elems);
    // public ImmutableList<E> remove(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public ImmutableList<E> remove(E... elems);
    // public ImmutableList<E> remove(E elem);
    // public ImmutableList<E> remove(int index);

    // public ImmutableList<E> union(Collection<E> elems);
    // public ImmutableList<E> union(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public ImmutableList<E> union(E... elems);

    // public ImmutableList<E> intersect(Collection<E> elems);
    // public ImmutableList<E> intersect(ImmutableList<E> elems);
    // public @SuppressWarnings({"unchecked", "varags"})
    // public ImmutableList<E> intersect(E... elems);

    // public <F> ImmutableList<F> map(Function<? super E, ? super F> mapper);
    // public <F> ImmutableList<E> filter(Predicate<? super E> predicate);
    // public Optional<E> reduce(BinaryOperator<E> accumulator);
    // public E reduce(E identity, BinaryOperator<E> accumulator);
    // public <F> F reduce(F identity, BiFunction<F, ? super E, F> accumulator, BinaryOperator<F> combiner);

    // Iterators & streams
    // public Iterator<E> iterator();
    // public Stream<E> stream();
    // public Stream<E> parallelStream();

    // Object methods
    // public ImmutableList<E> clone();
    // public boolean equals(Object o);
    // public int hashCode();

    // Conversions
    // public E[] toArray();
    // public List<E> asList();
}
