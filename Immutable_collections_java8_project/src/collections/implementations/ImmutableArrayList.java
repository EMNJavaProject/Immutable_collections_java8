package collections.implementations;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
		if(elems == null )
		{
			_array = (E[]) new Object[0];
			_length = 0;
		}
		else
		{
			_array =  elems;
			_length = _array.length;
		}

	}

	/**
	 * <p>Constructs a list containing the elements of the specified collection, in the order they are returned by the collection's iterator.
	 * <p>If the specified collection is null, constructs an empty ArrayList with a capacity of 0.
	 * @param elems - the collection whose elements are to be placed into this list
	 */
	@SuppressWarnings("unchecked")
	public ImmutableArrayList(Collection<E> elems)
	{
		if(elems == null )
		{
			_array = (E[]) new Object[0];
			_length = 0;
		}
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






	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;

	}


	public boolean isEmpty() { //TODO A tester
		return _length == 0 ? true : false;
	}


	public int size() { //TODO à tester.
		return _length;
	}


	public E get(int index) { //TODO a tester
		if(index >0 && index < _length)
			return _array[index];	
		else
			return null;
	}


	public int indexOf(E elem) {
		// TODO Auto-generated method stub
		return 0;
	}


	public E head() {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> tail() {
		// TODO Auto-generated method stub
		return null;
	}


	public E last() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ImmutableList<E> subList(int fromIndex, int toIndex)
			throws IndexOutOfBoundsException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ImmutableList<E> reverse() {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> sort(Comparator<? super E> comparator) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean contains(E elem) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean containsAll(Collection<E> elems) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean containsAll(ImmutableList<E> elems) {
		// TODO Auto-generated method stub
		return false;
	}


	@SuppressWarnings("unchecked")
	public boolean containsAll(E... elems) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean any(Predicate<? super E> predicate) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean all(Predicate<? super E> predicate) {
		// TODO Auto-generated method stub
		return false;
	}


	public ImmutableList<E> cons(E elem) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> concat(Collection<E> elems) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> concat(ImmutableList<E> elems) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public ImmutableList<E> concat(E... elems) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> concat(E elem) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> remove(Collection<E> elems) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> remove(ImmutableList<E> elems) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public ImmutableList<E> remove(E... elems) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> remove(E elem) {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> remove(int index) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ImmutableList<E> filter(Predicate<? super E> predicate) {
		// TODO Auto-generated method stub
		return null;
	}



	public Optional<E> reduce(BinaryOperator<E> accumulator) {
		// TODO Auto-generated method stub
		return null;
	}


	public E reduce(E identity, BinaryOperator<E> accumulator) {
		// TODO Auto-generated method stub
		return null;
	}


	public <F> F reduce(F identity, BiFunction<F, ? super E, F> accumulator,
			BinaryOperator<F> combiner) {
		// TODO Auto-generated method stub
		return null;
	}


	public Stream<E> stream() {
		// TODO Auto-generated method stub
		return null;
	}


	public Stream<E> parallelStream() {
		// TODO Auto-generated method stub
		return null;
	}


	public ImmutableList<E> clone() {
		// TODO Auto-generated method stub
		return null;
	}


	public E[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}


	public List<E> asList() {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public ImmutableList<E> add(E elem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImmutableList<E> add(int index, E elem) {
		// TODO Auto-generated method stub
		return null;
	}







}
