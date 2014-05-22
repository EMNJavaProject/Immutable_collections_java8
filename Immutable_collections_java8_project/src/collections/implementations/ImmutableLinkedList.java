package collections.implementations;

import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.Spliterators;
import java.util.Spliterator;
import java.util.Optional;
import java.util.List;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
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

	/** The last node element of the list. */
	private final Node<E> last;

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

	/**
	 * Returns the last node element of the list.
	 *
	 * @returns the last node element of the list.
	 **/
	private Node<E> lastNode() {
	    return last;
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
	    this.last = null;
	    this.size = 0;
	}

	/**
	 * Create a linked list containing the given elements in order.
	 *
	 * @param elems collection of elements to populate this list from
	 * @throws NullPointerException if elems is null
	 */
	@SuppressWarnings("unchecked")
	public ImmutableLinkedList(Collection<E> elems) {
	    this((E[])elems.toArray());
	}

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

	    Node<E> head = new Node<E>(elems[elems.length - 1]);
	    this.last = head;

	    for (int i = elems.length - 2 ; i >=0 ; --i)
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

	public int indexOf(E elem) {
	    int i = 0;
	    for (E other : this)
		if (equals(elem, other))
		    return i;
		else
		    ++i;
	    return -1;
	}

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

	public E last() throws NoSuchElementException {
	    if (isEmpty())
		throw new NoSuchElementException();
	    else
		return lastNode().getElement();
	}

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

	public ImmutableList<E> reverse() {
	    if (isEmpty())
		return this;
	    else
		return tail().reverse().concat(head());
	}

    // public List<E> sort(Comparator<? super E> comparator);

	/**
	 * Compare two objects according to Collection semantics.
	 *
	 * @param o1 the first object
	 * @param o2 the second object
	 * @return o1 == null ? o2 == null : o1.equals(o2)
	   */
	static final boolean equals(Object o1, Object o2) {
	    return o1 == null ? o2 == null : o1.equals(o2);
	}

	public boolean contains(E elem) {
	    return any((E other) -> equals(elem, other));
	}

	public boolean containsAll(Collection<E> elems) {
	    return containsAll(new ImmutableLinkedList<E>(elems));
	}

	public boolean containsAll(ImmutableList<E> elems) {
	    return elems.all((E elem) -> contains(elem));
	}

	public @SuppressWarnings({"unchecked", "varags"})
	boolean containsAll(E... elems) {
	    return containsAll(new ImmutableLinkedList<E>(elems));
	}

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

	@SuppressWarnings("unchecked")
	public ImmutableList<E> cons(E elem) {
	    E[] elems = (E[]) new Object[size()+1];
	    elems[0] = elem;

	    int i = 1;
	    for (E e : this) {
		elems[i] = e;
		++i;
	    }

	    return new ImmutableLinkedList(elems);
	}

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

	public ImmutableList<E> filter(Predicate<? super E> predicate) {
	    ImmutableList<E> res = new ImmutableLinkedList<E>();
	    for (E elem : this)
		if (predicate.test(elem))
		    res = res.concat(elem);
	    return res;
	}

	public Optional<E> reduce(BinaryOperator<E> accumulator) {
	    switch (size()) {
	    case 0 : return Optional.empty();
	    case 1 : return Optional.of(head());
	    default :

		E result = head();
		for (E elem : tail()) {
		    result = accumulator.apply(result, elem);
		}
		return Optional.of(result);
	    }
	}

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

	public Spliterator<E> spliterator() {
	    return Spliterators.spliterator(iterator(),
					    size(),
					    Spliterator.IMMUTABLE |
					    Spliterator.ORDERED   |
					    Spliterator.SIZED     |
					    Spliterator.SUBSIZED);
	}

	public Stream<E> stream() {
	    return StreamSupport.stream(spliterator(), false);
	}

	public Stream<E> parallelStream() {
	    return StreamSupport.stream(spliterator(), true);
	}

    // Object methods
    // public ImmutableList<E> clone();

	public boolean equals(Object o) {
	    if (! (o instanceof ImmutableList))
		return false;

	    ImmutableList other = (ImmutableList) o;

	    if (size() != other.size())
		return false;

	    Iterator<E> it1 = iterator();
	    Iterator it2 = other.iterator();

	    while (it1.hasNext()) {
		if (!equals(it1.next(), it2.next()))
		    return false;
	    }

	    return true;
	}

    // public int hashCode();

    // Conversions
    // public E[] toArray();
    // public List<E> asList();
}
