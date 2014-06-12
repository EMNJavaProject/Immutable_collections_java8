package collections.implementations;

import java.util.Collection;

import collections.interfaces.ImmutableList;

public class ImmutableLinkedList<E>  extends ImmutableBaseInductiveList<E> implements ImmutableList<E>{



	// Constructors

	/**
	 * Internal constructor which create a linked list given its attributes.
	 *
	 * @param head The first node of the list
	 * @param last The last node of the list
	 * @param size The size of the list
	 */
	private ImmutableLinkedList(Node<E> head, Node<E> last, int size) {
		super(head, last, size);
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
		super(elems);
	}



	@Override
	public ImmutableLinkedList<E> create(E[] elems) {
		return new ImmutableLinkedList<E>(elems);
	}

	@Override
	public <F> ImmutableLinkedList<F> create(Collection<F> elems) {
		return new ImmutableLinkedList<F>(elems);
	}

	@Override
	public  ImmutableLinkedList<E> create(Node<E> from, Node<E> head, int size){
		return new ImmutableLinkedList<E>(from, head, size);
	}
	
	

	// Operations


	public int size() {
		return size;
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
	public ImmutableLinkedList<E> tail() {
		return (ImmutableLinkedList<E>) super.tail();
	}

	
	
	@Override
	public ImmutableLinkedList<E> cons(E elem) {
		return (ImmutableLinkedList<E>) super.cons(elem);
	} 

}
