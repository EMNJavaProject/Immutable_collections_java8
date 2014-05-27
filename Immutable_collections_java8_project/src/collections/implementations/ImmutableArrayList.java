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

public class ImmutableArrayList<E> implements ImmutableList<E>
{

	private final E[] _array;
	private final int _length;


	/**
	 * Constructs an empty list with an initial capacity of 0.
	 */
	@SuppressWarnings("unchecked")
	public ImmutableArrayList ()
	{
		_array = (E[]) new Object[0];
		_length = 0;
	}

	/**
	 * <p>Constructs a list containing the given elements
	 * <p>If the specified collection is null, constructs an empty ArrayList with a capacity of 0.
	 * @param elems - the collection whose elements are to be placed into this list
	 */
	@SuppressWarnings("unchecked")
	public ImmutableArrayList (E... elems)
	{
		if (elems == null)
			throw new NullPointerException();
		else
		{
			_array =  elems;
			_length = _array.length;
		}

	}

	/**
	 * <p>Constructs a list containing the elements of the specified
	 * collection, in the order they are returned by the collection's
	 * iterator.
	 * <p>If the specified collection is null, constructs an empty ArrayList
	 * with a capacity of 0.
	 *
	 * @param elems - the collection whose elements are to be placed into this list
	 */
	@SuppressWarnings("unchecked")
	public ImmutableArrayList(Collection<E> elems)
	{
		if (elems == null)
			throw new NullPointerException();
		else
		{
			_array =  (E[])elems.toArray();
			_length = _array.length;
		}
	}

	/**
	 * Private accessor to get the inner array.
	 * @return the inner array.
	 */
	private E[] getArray() {
		return _array;
	}

	class ImmutableArrayListIterator implements Iterator<E> {

		/** Current node pointed by the iterator */
		private int index;
		private int size;

		/**
		 * Create a new iterator starting from the beginning of the linked list.
		 */
		public ImmutableArrayListIterator() {
			index = 0;
			size = size();
		}

		public boolean hasNext() {
			return index <= size-1 ? true : false;
		}

		public E next() throws NoSuchElementException {
			if(index >= size)
					throw new NoSuchElementException();

			E elem = getArray()[index];
			++index;
			return elem;
		}

		public void remove() throws
		UnsupportedOperationException,
		IllegalStateException {
			throw new UnsupportedOperationException();
		}
	}

	public Iterator<E> iterator() {
		return new ImmutableArrayListIterator();

	}


	public Spliterator<E> spliterator() {
		return Spliterators.spliterator(iterator(),
				size(),
				Spliterator.IMMUTABLE |
				Spliterator.ORDERED   |
				Spliterator.SIZED     |
				Spliterator.SUBSIZED);
	}

	public int size() {
		return _length;
	}


	public E get(int index) {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		if(index >=0 && index < _length)
			return _array[index];
		else
			return null;
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

	@Override
	public ImmutableList<E> subList(int fromIndex, int toIndex)
			throws IndexOutOfBoundsException, IllegalArgumentException {
		if (fromIndex < 0 || toIndex > size())
			throw new IndexOutOfBoundsException();
		if (fromIndex > toIndex)
			throw new IllegalArgumentException();
		if (fromIndex == toIndex)
			return new ImmutableLinkedList<E>();

		int j = 0;
		@SuppressWarnings("unchecked")
		E[] res = (E[]) new Object[toIndex - fromIndex];
		for(int i= fromIndex; i < toIndex; ++i)
		{
			res[j] = get(i);
			++j;
		}
		return new ImmutableArrayList<E>(res);
	}


	@Override
	public ImmutableList<E> reverse() {
		int j = 0;
		@SuppressWarnings("unchecked")
		E[] res = (E[]) new Object[size()];
		for(int i= size()-1; i >= 0; --i)
		{
			res[j] = get(i);
			++j;
		}
		return new ImmutableArrayList<E>(res);
	}


	public ImmutableList<E> sort(Comparator<? super E> comparator) {
		E[] a = toArray();
		Arrays.sort(a,(Comparator<? super E>) comparator);
		return new ImmutableLinkedList<E>(a);
	}

	public boolean contains(E elem) {
		return any((E other) -> equals(elem, other));
	}

	public boolean containsAll(Collection<E> elems) {
		return containsAll(new ImmutableArrayList<E>(elems));
	}

	public boolean containsAll(ImmutableList<E> elems) {
		return elems.all((E elem) -> contains(elem));
	}

	@SuppressWarnings("unchecked")
	public boolean containsAll(E... elems) {
		return containsAll(new ImmutableArrayList<E>(elems));
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


	public ImmutableList<E> cons(E elem) {
		@SuppressWarnings("unchecked")
		E[] elems = (E[]) new Object[size()+1];
		elems[0] = elem;

		int i = 1;
		for (E e : this) {
			elems[i] = e;
			++i;
		}

		return new ImmutableArrayList<E>(elems);
	}


	public ImmutableList<E> concat(Collection<E> elems) {
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


	public ImmutableList<E> concat(E elem) {
		@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
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
	public ImmutableList<E> remove(int index) { //TODO
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


	public ImmutableList<E> union(Collection<E> elems) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> union(ImmutableList<E> elems) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public ImmutableList<E> union(E... elems) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> intersect(Collection<E> elems) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> intersect(ImmutableList<E> elems) {
		// TODO Auto-generated method stub
		return null;
	}


	@SuppressWarnings("unchecked")
	public ImmutableList<E> intersect(E... elems) {
		// TODO Auto-generated method stub
		return null;
	}


	public <F> ImmutableList<F> map(Function<? super E, ? extends F> mapper) {
		@SuppressWarnings("unchecked")
		F[] elems = (F[]) new Object[size()];
		int i = 0;
		for (E elem : this) {
			elems[i] = mapper.apply(elem);
			++i;
		}

		return new ImmutableArrayList<F>(elems);
	}


	@Override
	public ImmutableList<E> filter(Predicate<? super E> predicate) {
		ImmutableList<E> res = new ImmutableArrayList<E>();
		for (E elem : this)
			if (predicate.test(elem))
				res = res.concat(elem);
		return res;
	}



	public Optional<E> reduce(BinaryOperator<E> accumulator) {
		switch (size()) {
		case 0 : return Optional.empty();
		case 1 : return Optional.of(get(0));
		default :

			E result = get(0);
			for (E elem : subList(1, size())) {
				result = accumulator.apply(result, elem);
			}
			return Optional.of(result);
		}
	}


	public Stream<E> stream() {
		return StreamSupport.stream(spliterator(), false);
	}


	public Stream<E> parallelStream() {
		return StreamSupport.stream(spliterator(), true);
	}


	public ImmutableList<E> clone() {
		return subList(0, size());
	}

	/**
	 * Hash an object.
	 *
	 * @param o the object to hash
	 * @return o1 == null ? 0 : o1.hashCode()
	 */
	static final int hashCode(Object o) {
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

	public E[] toArray() {
		return getArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <E> E[] toArray(E[] a) {
		return a;
	}

	public List<E> asList() {
		List<E> al = new ArrayList<E>(size());
		for(E e : this)
			al.add(e);
		return al;
	}



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

	public E head() throws NoSuchElementException {
		return null;
	}

	public ImmutableList<E> tail() throws UnsupportedOperationException {
		return null;
	}

	public E last() throws NoSuchElementException {
		return null;
	}
}
