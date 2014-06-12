package collections.implementations;

import java.util.Collection;

import collections.interfaces.ImmutableList;

public class ImmutableArrayList<E> extends ImmutableBaseIterativeList<E> implements ImmutableList<E>
{

	/**
	 * Constructs an empty list with an initial capacity of 0.
	 */
	public ImmutableArrayList ()
	{
		super();
	}

	/**
	 * <p>Constructs a list containing the given elements
	 * <p>If the specified collection is null, constructs an empty ArrayList with a capacity of 0.
	 * @param elems - the collection whose elements are to be placed into this list
	 */
	@SuppressWarnings("unchecked")
	public ImmutableArrayList (E... elems)
	{
		super(elems);
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
	public ImmutableArrayList(Collection<E> elems)
	{
		super(elems);
	}

	
	

	public ImmutableArrayList<E> create(E[] elems) {
		return new ImmutableArrayList<E>(elems);
	}

	public <F> ImmutableArrayList<F> create(Collection<F> elems) {
		return new ImmutableArrayList<F>(elems);
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


	
}
