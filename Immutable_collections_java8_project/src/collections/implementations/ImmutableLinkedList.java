package collections.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

		Node<E> head = new Node<E>(elems[elems.length - 1]);
		this.last = head;

		for (int i = elems.length - 2 ; i >=0 ; --i)
			head = new Node<E>(elems[i], head);

		this.head = head;
		this.size = elems.length;
	}

	public ImmutableList<E> create(E[] elems) {
		return new ImmutableLinkedList<E>(elems);
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
		Node<E> node = headNode();
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

	public boolean contains(E elem) {
		return any((E other) -> equals(elem, other));
	}

	public boolean containsAll(Collection<E> elems) {
		return containsAll(new ImmutableLinkedList<E>(elems));
	}

	public boolean containsAll(ImmutableList<E> elems) {
		return elems.all((E elem) -> contains(elem));
	}

	public @SuppressWarnings({"unchecked"})
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

	public ImmutableList<E> cons(E elem) {
		return new ImmutableLinkedList<E>(new Node<E>(elem, headNode()),
						  lastNode(),
						  size() + 1);
	}

	public ImmutableList<E> concat(Collection<E> elems){
		ImmutableList<E> list = this;
		for (E elem: elems){
			list = list.concat(elem);
		}
		return list;
	}
	public ImmutableList<E> concat(ImmutableList<E> elems) {
		ImmutableList<E> list = this;
		for (E elem : elems) {
			list = list.concat(elem);
		}
		return list;
	}
	@SuppressWarnings({"unchecked"})
	public ImmutableList<E> concat(E... elems){
		ImmutableList<E> list = this;
		for (int i = 0 ; i <elems.length ; ++i){
			list = list.concat(elems[i]);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public ImmutableList<E> concat(E elem) {
		E[] elems = (E[]) new Object[size() + 1];
		int i = 0;
		for (E e : this) {
			elems[i] = e;
			++i;
		}
		elems[size()] = elem;
		return new ImmutableLinkedList<E>(elems);
	}

	public ImmutableList<E> remove(Collection<E> elems) {
		ImmutableList<E> list = this;
		for (E elem: elems){
			list = list.remove(elem);
		}
		return list;

	}

	public ImmutableList<E> remove(ImmutableList<E> elems) {
		ImmutableList<E> list = this;
		for (E elem : elems) {
			list = list.remove(elem);
		}
		return list;
	}

	@SuppressWarnings({"unchecked"})
	public ImmutableList<E> remove(E... elems) {
		ImmutableList<E> list = this;
		for (int i = 0 ; i <elems.length ; ++i){
			list = list.remove(elems[i]);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public ImmutableList<E> remove(E elem) {
		E[] newElems;
		int i;
		boolean remove;
		newElems = (E[]) new Object[size() - 1];
		i = 0;
		remove = false;
		for ( E e :this){
			if(!remove && equals(elem,e))
				remove = true;
			else{
				if (i == this.size()-1)
					throw new IllegalArgumentException();
				newElems[i] = e;
				i++;
			}
		}

		return new ImmutableLinkedList<E>(newElems);
	}

	@SuppressWarnings("unchecked")
	public ImmutableList<E> remove(int index) {
		E[] newElems;
		int i;
		boolean remove;

		if (index >= size() || index < 0)
			throw new ArrayIndexOutOfBoundsException();

		newElems = (E[]) new Object[size() -1];
		i = 0;
		remove = false;
		for ( E e :this){
			if(!remove && i==index)
				remove = true;
			else{
				newElems[i] = e;
				++i;
			}
		}
		return new ImmutableLinkedList<E>(newElems);
	}

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

	public boolean equals(Object o) {
		if (! (o instanceof ImmutableList))
			return false;

		@SuppressWarnings("rawtypes")
		ImmutableList other = (ImmutableList) o;

		if (size() != other.size())
			return false;

		Iterator<E> it1 = iterator();
		@SuppressWarnings("rawtypes")
		Iterator it2 = other.iterator();

		while (it1.hasNext()) {
			if (!equals(it1.next(), it2.next()))
				return false;
		}

		return true;
	}

	/**
	 * Compare two objects according to Collection semantics.
	 *
	 * @param o1 the first object
	 * @param o2 the second object
	 * @return o1 == null ? o2 == null : o1.equals(o2)
	 */
	static boolean equals(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}

	/**
	 * Hash an object.
	 *
	 * @param o the object to hash
	 * @return o1 == null ? 0 : o1.hashCode()
	 */
	static int hashCode(Object o) {
		return o == null ? 0 : o.hashCode();
	}

	public int hashCode() {
		int hashCode = 1;
		Iterator<E> itr = iterator();
		int pos = size();
		while (--pos >= 0)
			hashCode = 31 * hashCode + hashCode(itr.next());
		return hashCode;
	}

	// Conversions
	@SuppressWarnings("unchecked")
	public E[] toArray(){
		return toArray((E[]) new Object[size()]);
	}

	@SuppressWarnings({ "unchecked", "hiding" })
	public <E> E[] toArray(E[] a){
		int i;
		E[] result;
		i=0;
		result = a  ;
		for (Node<E> x = (Node<E>) this.head; x != null; x = x.getNext()){
			result[i++] = x.getElement();
		}
		return a;
	}

	public List<E> asList(){
		List<E> myList = new ArrayList<E>();

		for (E e : this){
			myList.add(e);
		}
		return myList;
	}

	public ImmutableList<E> clone() {
		return subList(0, size());
	}

}
