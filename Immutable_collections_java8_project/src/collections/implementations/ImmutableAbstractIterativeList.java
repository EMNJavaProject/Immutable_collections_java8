package collections.implementations;

import collections.interfaces.ImmutableCoreList;
import collections.interfaces.IterativeList;

public abstract class ImmutableAbstractIterativeList<E> implements IterativeList<E> 
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

}
