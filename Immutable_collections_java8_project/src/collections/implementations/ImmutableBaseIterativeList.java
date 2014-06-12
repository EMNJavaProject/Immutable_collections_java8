package collections.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import collections.interfaces.ImmutableCoreList;
import collections.interfaces.IterativeList;


public  class ImmutableBaseIterativeList<E> implements IterativeList<E> 
{

	private final E[] _array;
	private final int _length;
	
	@SuppressWarnings("unchecked")
	public ImmutableBaseIterativeList()
	{
		_array = (E[]) new Object[0];
		_length = 0;
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
	public ImmutableCoreList<E> create(E[] elems) {
		return new ImmutableArrayList<E>(elems);
	}

	@Override
	public <F> ImmutableCoreList<F> create(Collection<F> elems) {
		return new ImmutableArrayList<F>(elems);
	}

	@Override
	public boolean isEmpty() {
		return this._length==0;
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
