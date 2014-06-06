package collections.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import collections.interfaces.InductiveIterativeList;
import collections.interfaces.IterativeList;
import collections.interfaces.ImmutableList;

public class ImmutableArrayList<E> implements InductiveIterativeList<E>
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
		else if (elems.length == 0) {
			this._array  = (E[]) new Object[0];
			this._length = 0;
		}
		else
		{
			_array =  elems;
			_length = _array.length;
		}
	}

	public ImmutableArrayList<E> create(E[] elems) {
		return new ImmutableArrayList<E>(elems);
	}

	public <F> ImmutableArrayList<F> create(Collection<F> elems) {
		return new ImmutableArrayList<F>(elems);
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
		 * Create a new iterator starting from the beginning of the list.
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

	@Override
	public ImmutableArrayList<E> subList(int fromIndex, int toIndex)
			throws IndexOutOfBoundsException, IllegalArgumentException {
		if (fromIndex < 0 || toIndex > size())
			throw new IndexOutOfBoundsException();
		if (fromIndex > toIndex)
			throw new IllegalArgumentException();
		if (fromIndex == toIndex)
			return new ImmutableArrayList<E>();

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
	public ImmutableArrayList<E> reverse() {
		return new ImmutableReversedArrayList<E>(this);
	}

	@SuppressWarnings("unchecked")
	public ImmutableArrayList<E> remove(int index) {
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
		return new ImmutableArrayList<E>(newElems);
	}

	public E[] toArray() {
		return getArray();
	}

	public ImmutableArrayList<E> cons(E elem) {
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


	public int hashCode() {
		return ImmutableList.hashCode(this);
	}

	public InductiveIterativeList<E> clone() {
		return InductiveIterativeList.clone(this);
	}

	public boolean equals(Object o) {
		return IterativeList.equals(this, o);
	}
}
