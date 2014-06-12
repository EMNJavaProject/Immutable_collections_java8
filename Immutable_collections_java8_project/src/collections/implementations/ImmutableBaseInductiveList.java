package collections.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import collections.interfaces.ImmutableCoreList;
import collections.interfaces.InductiveList;



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

public abstract class ImmutableBaseInductiveList <E> implements InductiveList<E> {

	/** The first node element of the list. */
	protected final Node<E> head;

	/** The last node element of the list. */
	protected final Node<E> last;

	/** The number of elements in this list. */
	protected final int size;


	/**
	 * Internal constructor which create a linked list given its attributes.
	 *
	 * @param head The first node of the list
	 * @param last The last node of the list
	 * @param size The size of the list
	 */
	public ImmutableBaseInductiveList(Node<E> head, Node<E> last, int size) {
		this.head = head;
		this.last = last;
		this.size = size;
	}

	/**
	 * Create a empty linked list.
	 */
	public ImmutableBaseInductiveList() {
		this(null, null, 0);
	}

	/**
	 * Create a linked list containing the given elements in order.
	 *
	 * @param elems collection of elements to populate this list from
	 * @throws NullPointerException if elems is null
	 */
	@SuppressWarnings("unchecked")
	public ImmutableBaseInductiveList(Collection<E> elems) {
		this((E[])elems.toArray());
	}

	/**
	 * Create a linked list containing the given elements in order.
	 *
	 * @param elems the elements to populate this list from
	 * @throws NullPointerException if elems is null
	 */
	@SuppressWarnings({"unchecked"})
	public ImmutableBaseInductiveList(E... elems) {
		if (elems == null)
			throw new NullPointerException();

		if (elems.length == 0) {
			this.head = null;
			this.last = null;
			this.size = 0;
			return;
		}

		Node<E> head = new Node<E>(elems[elems.length - 1]);
		this.last = head;

		for (int i = elems.length - 2 ; i >=0 ; --i)
			head = new Node<E>(elems[i], head);

		this.head = head;
		this.size = elems.length;
	}





	/**
	 * Returns the first node element of the list.
	 *
	 * @returns the first node element of the list.
	 **/
	protected Node<E> headNode() {
		return head;
	}

	/**
	 * Returns the last node element of the list.
	 *
	 * @returns the last node element of the list.
	 **/
	protected Node<E> lastNode() {
		return last;
	}

	protected int size() {
		return size;
	}

	@Override
	public InductiveList<E> clone() { 
		return (InductiveList<E>) ImmutableCoreList.clone(this); 
	}

	@Override
	public boolean equals(Object o) {
		return ImmutableCoreList.equals(this, o);
	}

	@Override
	public int hashCode() {
		return ImmutableCoreList.hashCode(this);
	}

	@Override
	public InductiveList<E> cons(E elem) {
		return  create(new Node<E>(elem, headNode()),
				lastNode(),
				size() + 1);
	} 

	@Override
	public E head() throws NoSuchElementException { 
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return headNode().getElement();
	}


	@Override
	public InductiveList<E> tail() {
		if (isEmpty())
			throw new UnsupportedOperationException();
		else
		{
			final int fromIndex = 1;
			final int toIndex = size();
			if (fromIndex < 0 || toIndex > size())
				throw new IndexOutOfBoundsException();
				if (fromIndex > toIndex)
				throw new IllegalArgumentException();
				if (fromIndex == toIndex)
				return new ImmutableLinkedList<E>();

				int i = 0;
				Node<E> node = headNode();
				while (i != fromIndex) {
				node = node.getNext();
				++i;
				}
				Node<E> subListHead = node;

				while (i != toIndex-1) {
				node = node.getNext();
				++i;
				}
				Node<E> subListLast = node;

				return create(subListHead,
				subListLast,
				toIndex - fromIndex);
		}
	}

	public abstract ImmutableLinkedList<E> create(Node<E> from, Node<E> head, int size);

	@Override
	public E last() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return lastNode().getElement();
	}



	@Override
	public Iterator<E> iterator() {
		return new ImmutableLinkedListIterator();
	}



	@Override
	public boolean isEmpty() {
		return head==null;
	}

	// Iterators & streams

	class ImmutableLinkedListIterator implements Iterator<E> { //TODO change name ?

		/** Current node pointed by the iterator */
		private Node<E> currentNode;

		/** Tell whether the iterator can continue or not */
		private boolean hasNext;

		/**
		 * Create a new iterator starting from the beginning of the linked list.
		 */
		public ImmutableLinkedListIterator() {
			currentNode = headNode();
			hasNext = (size() != 0);
		}

		public boolean hasNext() {
			return hasNext;
		}

		public E next() throws NoSuchElementException {
			if (!hasNext())
				throw new NoSuchElementException();

			E elem = currentNode.getElement();
			if (currentNode == lastNode())
				hasNext = false;
			else
				currentNode = currentNode.getNext();
			return elem;
		}

		public void remove() throws
		UnsupportedOperationException,
		IllegalStateException {
			throw new UnsupportedOperationException();
		}
	}
}


