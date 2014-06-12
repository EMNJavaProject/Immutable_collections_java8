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

public  class ImmutableBaseInductiveList <E> implements InductiveList<E> {
	
	/** The first node element of the list. */
	private final Node<E> head;

	/** The last node element of the list. */
	private final Node<E> last;
	
	/** The number of elements in this list. */
	private final int size;


	public ImmutableBaseInductiveList(Node<E> head, Node<E> last, int size) {
		this.head = head;
		this.last = last;
		this.size = size;
	}
	
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

	private int size() {
		return size;
	}
	
	@Override
	public ImmutableCoreList<E> clone() { //TODO check static returned type.
		return ImmutableCoreList.clone(this); //TODO check call
	}
	
	@Override
	public boolean equals(Object o) {
		return ImmutableCoreList.equals(this, o);//TODO check call to first background interface while InductiveList<E>
	}
	
	@Override
	public int hashCode() {
		return ImmutableCoreList.hashCode(this); //TODO check call to first background interface while InductiveList<E>
	}
	
	@Override
	public InductiveList<E> cons(E elem) {
		return new ImmutableBaseInductiveList<E>(new Node<E>(elem, headNode()),
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
	
	@SuppressWarnings("unchecked")
	@Override
	public InductiveList<E> tail() {

		int i = 1;
		Node<E> node = headNode().getNext();
		Node<E> subListHead = headNode().getNext();

		while (i != this.size-1) {
			node = node.getNext();
			++i;
		}
		Node<E> subListLast = node;

		return new ImmutableBaseInductiveList<E>(subListHead,
						  subListLast,
						  this.size - 1);
	}
	
	@Override
	public E last() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return lastNode().getElement();
	}

	@Override
	public ImmutableCoreList<E> create(E[] elems) {
		return new ImmutableLinkedList<E>(elems);
	}

	@Override
	public <F> ImmutableCoreList<F> create(Collection<F> elems) {
		return new ImmutableLinkedList<F>(elems);
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

	class ImmutableLinkedListIterator implements Iterator<E> {

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


