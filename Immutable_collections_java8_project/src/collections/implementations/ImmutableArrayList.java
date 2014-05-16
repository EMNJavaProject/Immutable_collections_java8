package collections.implementations;

import java.util.ArrayList;
import java.util.Collection;

public class ImmutableArrayList<E> {

	private final E[] _array;


	/**
	 * Constructs an empty list with an initial capacity of 0.
	 */
	public ImmutableArrayList ()
	{
		_array = (E[]) new Object[0];
	}
	
	/**
	 * <p>Constructs a list containing the given elements
	 * <p>If the specified collection is null, constructs an empty ArrayList with a capacity of 0.
	 * @param elems - the collection whose elements are to be placed into this list
	 */
	public ImmutableArrayList (E... elems)
	{	
		if(elems == null)
			_array = (E[]) new Object[0];
		else
			_array =  elems;

	}
	
	/**
	 * <p>Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator.
	 * <p>If the specified collection is null, constructs an empty ArrayList with a capacity of 0.
	 * @param elems - the collection whose elements are to be placed into this list
	 */
	public ImmutableArrayList(Collection<E> elems)
	{
		if(elems == null)
			_array = (E[]) new Object[0];
		else
			_array = (E[]) elems.toArray();
	}

	


	private E[] getArray() {
		return _array;
	}




}
