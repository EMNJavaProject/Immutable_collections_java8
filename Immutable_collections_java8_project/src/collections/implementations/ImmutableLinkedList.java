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
	    for (int i = elems.length - 1 ; i >=0 ; --i)
		head = new Node<E>(elems[i], head);

	    this.head = head;
	    this.size = elems.length;
	}

        // Operations

        public boolean isEmpty() {
	    return size() == 0;
	}

	public E get(int index) throws IndexOutOfBoundsException {
	    if (index < 0 || index >= size())
		throw new IndexOutOfBoundsException();

	    int i = 0;
	    for (E elem : this) {
		if (i == index)
		    return elem;
		++i;
	    }

	    return null; // Never happens
	}

    // public int indexOf(E elem);

	public E head() throws NoSuchElementException {
	    if (isEmpty())
		throw new NoSuchElementException();
	    else
		return headNode().getElement();
	}

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

	public boolean any(Predicate<? super E> predicate) {
	    for (E elem : this)
		if (predicate.test(elem))
		    return true;
	    return false;
	}

	public boolean all(Predicate<? super E> predicate) {
	    for (E elem : this)
		if (!predicate.test(elem))
		    return false;
	    return true;
	}

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
	    int i = 0;
	    for (E e : this) {
		elems[i] = e;
		++i;
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

	@SuppressWarnings("unchecked")
	public <F> ImmutableList<F> map(Function<? super E, ? extends F> mapper) {
	    F[] elems = (F[]) new Object[size()];
	    int i = 0;
	    for (E elem : this) {
		elems[i] = mapper.apply(elem);
		++i;
	    }

	    return new ImmutableLinkedList<F>(elems);
	}

    // public <F> ImmutableList<E> filter(Predicate<? super E> predicate);
    // public Optional<E> reduce(BinaryOperator<E> accumulator);
    // public E reduce(E identity, BinaryOperator<E> accumulator);
    // public <F> F reduce(F identity, BiFunction<F, ? super E, F> accumulator, BinaryOperator<F> combiner);

        // Iterators & streams

	class ImmutableLinkedListIterator implements Iterator<E> {

	    /** Current node pointed by the iterator */
	    private Node<E> currentNode;

	    /**
	     * Create a new iterator starting from the beginning of the linked list.
	     */
	    public ImmutableLinkedListIterator() {
		currentNode = headNode();
	    }

	    public boolean hasNext() {
		return currentNode != null;
	    }

	    public E next() throws NoSuchElementException {
		if (currentNode == null)
		    throw new NoSuchElementException();

		E elem = currentNode.getElement();
		currentNode = currentNode.getNext();
		return elem;
	    }

	    public void remove() throws
		UnsupportedOperationException,
		IllegalStateException {
		throw new UnsupportedOperationException();
	    }
	}

	public Iterator<E> iterator() {
	    return new ImmutableLinkedListIterator();
	}


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
