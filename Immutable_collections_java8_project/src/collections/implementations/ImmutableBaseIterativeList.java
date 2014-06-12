package collections.implementations;

import java.util.Collection;
import java.util.Iterator;

import collections.interfaces.ImmutableCoreList;
import collections.interfaces.IterativeList;

public  class ImmutableBaseIterativeList<E> implements IterativeList<E> 
{
	
	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		return null; //TODO refers to the implementation class
	}
	
	@Override
	public boolean equals(Object o) {
		return IterativeList.equals(this, o); //TODO check "this" validity (abstract class)
	}

	@Override
	public ImmutableCoreList<E> clone() { //TODO check static returned type
		return ImmutableCoreList.clone(this); //TODO check "this" validity (abstract class)
	}
	
	@Override
	public int hashCode() {
		return ImmutableCoreList.hashCode(this);  //TODO check "this" validity (abstract class)
	}

	@Override
	public ImmutableCoreList<E> create(E[] elems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <F> ImmutableCoreList<F> create(Collection<F> elems) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
