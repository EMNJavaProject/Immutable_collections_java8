package collections.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import collections.interfaces.ImmutableCoreList;
import collections.interfaces.ImmutableList;
import collections.interfaces.InductiveList;
import collections.interfaces.IterativeList;

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
	 * Internal constructor which create a linked list given its attributes.
	 *
	 * @param head The first node of the list
	 * @param last The last node of the list
	 * @param size The size of the list
	 */
	private ImmutableLinkedList(Node<E> head, Node<E> last, int size) {
		this.head = head;
		this.last = last;
		this.size = size;
	}

	/**
	 * Create a empty linked list.
	 */
	public ImmutableLinkedList() {
		this(null, null, 0);
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
	@SuppressWarnings({"unchecked"})
	public ImmutableLinkedList(E... elems) {
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

	public ImmutableLinkedList<E> create(E[] elems) {
		return new ImmutableLinkedList<E>(elems);
	}

	public <F> ImmutableLinkedList<F> create(Collection<F> elems) {
		return new ImmutableLinkedList<F>(elems);
	}

	// Operations

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

	public E head() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return headNode().getElement();
	}

	public E last() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		else
			return lastNode().getElement();
	}

	public ImmutableLinkedList<E> subList(int fromIndex, int toIndex) throws
		IndexOutOfBoundsException,
		IllegalArgumentException {

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

		return new ImmutableLinkedList<E>(subListHead,
						  subListLast,
						  toIndex - fromIndex);
	}

	// Factories

	public ImmutableLinkedList<E> cons(E elem) {
		return new ImmutableLinkedList<E>(new Node<E>(elem, headNode()),
						  lastNode(),
						  size() + 1);
	}

	@SuppressWarnings("unchecked")
	public ImmutableLinkedList<E> remove(int index) {
		E[] newElems;
		int i;
		boolean remove;

		if (index >= size() || index < 0)
			throw new ArrayIndexOutOfBoundsException();

		newElems = (E[]) new Object[size() -1];
		i = 0;
		remove = false;
		for ( E e : this){
			if(!remove && i==index)
				remove = true;
			else{
				newElems[i] = e;
				++i;
			}
		}
		return new ImmutableLinkedList<E>(newElems);
	}

	@Override
	public ImmutableList<E> tail() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImmutableCoreList<E> clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
