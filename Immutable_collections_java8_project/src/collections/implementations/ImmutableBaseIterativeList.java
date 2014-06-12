package collections.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import collections.interfaces.ImmutableCoreList;
import collections.interfaces.IterativeList;


public  abstract class ImmutableBaseIterativeList<E> implements IterativeList<E> 
{

	protected final E[] _array;
	protected final int _length;
	
	
	
	/**
	 * Constructs an empty list with an initial capacity of 0.
	 */
	@SuppressWarnings("unchecked")
	public ImmutableBaseIterativeList()
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
	public ImmutableBaseIterativeList (E... elems)
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
	public ImmutableBaseIterativeList(Collection<E> elems)
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
	protected E[] getArray() {
		return _array;
	}
	
	
	
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		if(index >=0 && index < size())
			return _array[index];
		else
			return null;
	}
	
	@Override
	public boolean equals(Object o) {
		return IterativeList.equals(this, o); 
	}

	@Override
	public ImmutableCoreList<E> clone() { 
		return ImmutableCoreList.clone(this); 
	}
	
	@Override
	public int hashCode() {
		return ImmutableCoreList.hashCode(this);
	}

	@Override
	public Iterator<E> iterator() {
		return new ImmutableArrayListIterator();
	}

	@Override
	public int size() {
		return _length;
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

			E elem = _array[index];
			++index;
			return elem;
		}

		public void remove() throws
		UnsupportedOperationException,
		IllegalStateException {
			throw new UnsupportedOperationException();
		}
	}
	
}
