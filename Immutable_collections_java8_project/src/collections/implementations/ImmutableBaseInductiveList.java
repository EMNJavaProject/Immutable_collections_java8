package collections.implementations;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import collections.interfaces.ImmutableCoreList;
import collections.interfaces.InductiveList;

public  class ImmutableBaseInductiveList <E> implements InductiveList<E> {

	@Override
	public boolean isEmpty() {
		return false; //TODO find why this method is not implemented in concrete class (ImmutableLinkedList).
	}
	
	@Override
	public ImmutableCoreList<E> clone() { //TODO check static returned type.
		return ImmutableCoreList.clone(this); //TODO check call
	}
	
	@Override
	public boolean equals(Object o) {
		return ImmutableCoreList.equals(this, o);//TODO check call to first background interface while InductiveList<E>
	}
	
	@Override
	public int hashCode() {
		return ImmutableCoreList.hashCode(this); //TODO check call to first background interface while InductiveList<E>
	}
	
	@Override
	public InductiveList<E> cons(E elem) {
		return null; // new ImmutableLinkedList<E>(new Node<E>(elem, head()), last(), size() + 1); //TODO add size in Core ?
	} 
	
	@Override
	public E head() throws NoSuchElementException { //TODO implementation class call an accessor method, check it.
		return null;
	}
	
	@Override
	public InductiveList<E> tail() throws UnsupportedOperationException { //TODO ERROR : check why tail is not implemented in the implementation class...
		return null;
	}
	
	@Override
	public E last() throws NoSuchElementException { //TODO implementation class call an accessor method, check it.
		return null;
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
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
