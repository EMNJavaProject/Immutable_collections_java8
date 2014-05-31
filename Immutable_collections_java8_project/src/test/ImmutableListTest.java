package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

import collections.implementations.ImmutableArrayList;
import collections.implementations.ImmutableLinkedList;
import collections.interfaces.ImmutableList;

interface ImmutableListFactory<E> {
	@SuppressWarnings("unchecked") public ImmutableList<E> create(E... elems);
	public ImmutableList<E> create();
}

class ImmutableLinkedListFactory<E> implements ImmutableListFactory<E> {
	@SuppressWarnings({"unchecked"})
	public ImmutableList<E> create(E... elems) {
		return new ImmutableLinkedList<E>(elems);
	}
	public ImmutableList<E> create() {
		return new ImmutableLinkedList<E>();
	}
}

class ImmutableArrayListFactory<E> implements ImmutableListFactory<E> {
	@SuppressWarnings({"unchecked"})
	public ImmutableList<E> create(E... elems) {
		return new ImmutableArrayList<E>(elems);
	}
	public ImmutableList<E> create() {
		return new ImmutableArrayList<E>();
	}
}

class ReversedArrayListFactory<E> implements ImmutableListFactory<E> {
	@SuppressWarnings("unchecked")
	public ImmutableList<E> create(E... _elems) {
		List<E> tmp = Arrays.asList(_elems);
		Collections.reverse(tmp);
		E[] elems = (E[]) tmp.toArray();

		return new ImmutableArrayList<E>(elems).reverse();
	}
	public ImmutableList<E> create() {
		return new ImmutableArrayList<E>().reverse();
	}
}

public abstract class ImmutableListTest {

	protected ImmutableList<Integer> list;
	protected ImmutableList<Integer> emptyList;
	protected ImmutableListFactory<Integer> factory;

	public void setUp(ImmutableListFactory<Integer> factory) {
		this.factory = factory;
		list      = factory.create(1, 2, 3);
		emptyList = factory.create();
	}

	@Test(expected=NullPointerException.class)
	public void ConstructorExceptionTest() {
		factory.create((Integer[])null);
	}

	@Test
	public void isEmptyTest() {
		assertFalse(list.isEmpty());
		assertTrue(emptyList.isEmpty());
	}

	@Test
	public void indexOf() {
		assertEquals(1 , list.indexOf(2));
		assertEquals(-1, list.indexOf(4));
	}

	@Test
	public void GetTest() {
		assertEquals(1, (int)list.get(0));
		assertEquals(2, (int)list.get(1));
		assertEquals(3, (int)list.get(2));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void GetExceptionTest() {
		assertEquals(0, (int)list.get(-1));
		assertEquals(0, (int)list.get(3));
	}

	@Test
	public void SizeTest() {
		assertEquals(3, list.size());
	}

	@Test
	public void SubListTest() {
		ImmutableList<Integer> empty = list.subList(0, 0);
		assertTrue(empty.isEmpty());

		ImmutableList<Integer> subList = list.subList(1, 3);
		assertEquals(2, subList.size());
		assertEquals(2, (int)subList.get(0));
		assertEquals(3, (int)subList.get(1));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void SubListExceptionTest1() {
		list.subList(-1, 1);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void SubListExceptionTest2() {
		list.subList(0, list.size()+1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void SubListExceptionTest3() {
		list.subList(2, 1);
	}

	@Test
	public void MapTest() {
		ImmutableList<Integer> mappedList = list.map((Integer x) -> x * 2);
		assertEquals(mappedList.size(), list.size());
		assertEquals((int)mappedList.get(0), (int)2 * list.get(0));
		assertEquals((int)mappedList.get(1), (int)2 * list.get(1));
		assertEquals((int)mappedList.get(2), (int)2 * list.get(2));
	}

	@Test
	public void FilterTest() {
		ImmutableList<Integer> filteredList = list.filter((Integer x) -> x % 2 != 0);
		assertEquals(filteredList, new ImmutableLinkedList<Integer>(1, 3));

		filteredList = list.filter((Integer x) -> true);
		assertEquals(filteredList, list);

		filteredList = list.filter((Integer x) -> false);
		assertEquals(filteredList, emptyList);
	}

	@Test
	public void ReduceTest() {
		Optional<Integer> reduced = list.reduce((Integer x, Integer y) -> x + y);
		assertEquals(6, (int)reduced.get());

		reduced = list.reduce((Integer x, Integer y) -> y);
		assertEquals(3, (int)reduced.get());

		reduced = emptyList.reduce((Integer x, Integer y) -> x + y);
		assertEquals(Optional.empty(), reduced);

		reduced = emptyList.concat(4).reduce((Integer x, Integer y) -> x + y);
		assertEquals(4, (int)reduced.get());
	}

	@Test(expected=NullPointerException.class)
	public void ReduceExceptionTest() {
		list.reduce((Integer x, Integer y) -> null);
	}

	@Test
	public void AnyTest() {
		assertTrue (list.any((Integer x) -> x % 2 == 0));
		assertFalse(list.any((Integer x) -> x < 0));
	}

	@Test
	public void AllTest() {
		assertTrue (list.all((Integer x) -> x < 10));
		assertFalse(list.all((Integer x) -> x % 2 == 0));
	}

	@Test
	public void IteratorTest() {
		Iterator<Integer> it = list.iterator();

		assertTrue(it.hasNext()); assertEquals(1, (int)it.next());
		assertTrue(it.hasNext()); assertEquals(2, (int)it.next());
		assertTrue(it.hasNext()); assertEquals(3, (int)it.next());
		assertFalse(it.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void IteratorExceptionTest1() {
		Iterator<Integer> it = emptyList.iterator();
		it.next();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void IteratorExceptionTest2() {
		Iterator<Integer> it = list.iterator();
		it.remove();
	}

	@Test
	public void SubListIteratorTest() {

		// Test that iterators stop at the right last element for sublists
		// (since they all share their structures with a superlist)

		ImmutableList<Integer> subList = list.subList(1, list.size()-1);
		assertEquals(1, subList.size());

		int sizeWhenIterating = 0;
		for (Integer i : subList)
			++sizeWhenIterating;

		assertEquals(subList.size(), sizeWhenIterating);
	}

	@Test
	public void ContainsTest() {
		assertTrue(list.contains(1));
		assertTrue(list.contains(2));
		assertTrue(list.contains(3));

		assertFalse(list.contains(4));

		assertFalse(list.contains(null));
		list = list.concat((Integer)null);
		assertTrue(list.contains(null));
	}

	@Test
	public void ContainsAllTest1() {
		assertTrue(list.containsAll(1, 2, 3));
		assertTrue(list.containsAll(1, 2));
		assertFalse(list.containsAll(1, 2, 4));
		assertFalse(list.containsAll(1, 2, null));
	}

	@Test
	public void ContainsAllTest2() {
		List<Integer> otherList = new ArrayList<Integer>();
		otherList.add(1);
		otherList.add(2);

		assertTrue (list.containsAll(otherList));

		otherList.add(3);
		assertTrue (list.containsAll(otherList));

		otherList.add(4);
		assertFalse(list.containsAll(otherList));
	}

	@Test
	public void ContainsAllTest3() {
		assertTrue (list.containsAll(new ImmutableLinkedList<Integer>(1, 2, 3)));
		assertTrue (list.containsAll(new ImmutableLinkedList<Integer>(1, 2)));
		assertFalse(list.containsAll(new ImmutableLinkedList<Integer>(1, 2, 4)));
		assertFalse(list.containsAll(new ImmutableLinkedList<Integer>(1, 2, null)));
	}

	@Test
	public void consTest() {
		list = list.cons(4);
		assertEquals(4, list.size());
		assertEquals(4, (int)list.get(0));
		assertEquals(1, (int)list.get(1));
		assertEquals(2, (int)list.get(2));
		assertEquals(3, (int)list.get(3));
	}

	@Test
	public void reverseTest() {
		assertEquals(emptyList, emptyList.reverse());
		assertEquals(new ImmutableLinkedList<Integer>(3, 2, 1), list.reverse());
	}

	@Test
	public void EqualsTest() {
		assertEquals(emptyList, new ImmutableLinkedList<Integer>());
		assertFalse(emptyList.equals(list));

		assertFalse(list.equals(new ImmutableLinkedList<Integer>(1, 2, 3, 4)));
		assertEquals(list, new ImmutableLinkedList<Integer>(1, 2, 3));
	}

	@Test
	public void StreamTest() {
		assertEquals(6, (int)list.stream()        .reduce((Integer x, Integer y) -> x + y).get());
		assertEquals(6, (int)list.parallelStream().reduce((Integer x, Integer y) -> x + y).get());

		assertEquals(12, (int)list.stream()
			     .map   ((Integer x) -> x * 2)
			     .reduce((Integer x, Integer y) -> x + y).get());
		assertEquals(12, (int)list.parallelStream()
			     .map   ((Integer x) -> x * 2)
			     .reduce((Integer x, Integer y) -> x + y).get());

		assertEquals(3, (int)list.stream()        .max((Integer x, Integer y) -> x.compareTo(y)).get());
		assertEquals(3, (int)list.parallelStream().max((Integer x, Integer y) -> x.compareTo(y)).get());

		assertEquals(6, Stream.concat(list.stream()        , list.stream()).count());
		assertEquals(6, Stream.concat(list.parallelStream(), list.parallelStream()).count());
		assertEquals(6, Stream.concat(list.stream()        , list.parallelStream()).count());
		assertEquals(6, Stream.concat(list.parallelStream(), list.stream()).count());

		assertEquals(list.stream().findFirst().get(), list.reverse().stream().sorted().findFirst().get());
		assertEquals(list.parallelStream().findFirst().get(), list.reverse().parallelStream().sorted().findFirst().get());

		assertEquals(1, (int)list.stream().findFirst().get());
		assertEquals(1, (int)list.parallelStream().findFirst().get());

		assertEquals(2, list.stream().limit(2).count());
		assertEquals(2, list.parallelStream().limit(2).count());
	}

	@Test
	public void removeTest1() {
		Integer second = list.get(1);
		Integer third  = list.get(2);

		assertEquals(new ImmutableLinkedList<Integer>(second, third),list.remove(0));

		assertEquals(new ImmutableLinkedList<Integer>(second, third),list.remove(new Integer(1)));
		assertEquals(new ImmutableLinkedList<Integer>(third),list.remove(new Integer(1), new Integer(2)));
		assertEquals(new ImmutableLinkedList<Integer>(second), list.remove(new ImmutableLinkedList<Integer>(1,3)));
		List<Integer> otherList = new ArrayList<Integer>();
		otherList.add(1);
		otherList.add(3);
		assertEquals(new ImmutableLinkedList<Integer>(second), list.remove(otherList));
	}

	@Test
	public void ConcatTest() {
		list = list.concat(4);
		assertEquals(4, (int)list.get(3));
		assertEquals(new ImmutableLinkedList<Integer>(1, 2, 3, 4, 5, 6),list.concat(new Integer(5),new Integer(6)));
		assertEquals(new ImmutableLinkedList<Integer>(1, 2, 3, 4, 5, 6),list.concat(new ImmutableLinkedList<Integer>(5,6)));
		List<Integer> otherList = new ArrayList<Integer>();
		otherList.add(5);
		otherList.add(6);
		assertEquals(new ImmutableLinkedList<Integer>(1, 2, 3, 4, 5, 6),list.concat(otherList));
	}


	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void removeTest2(){
		list.remove(3);
	}

	@Test(expected=IllegalArgumentException.class)
	public void removeTest3(){
		list.remove(new Integer(4));
	}

	@Test
	public void cloneTest(){
		assertEquals(list.clone(),list);
		assertFalse(list==list.clone());
	}

	@Test
	public void hashCodeTest() {
		assertFalse(emptyList.hashCode() == list.hashCode());

		assertEquals(list.hashCode(), factory.create(1, 2, 3).hashCode());
		assertEquals(emptyList.hashCode(), factory.create().hashCode());
	}

	@Test
	public void toarrayTest(){
		Integer[] array= { 1 ,2 ,3 };
		Integer[] array2 =  new Integer[array.length];
		array2=list.toArray(array); //TODO before : array2=list.toArray(array2) -> Check validity
		assertEquals(array2[0],array[0]);
		assertEquals(array2[1],array[1]);
		assertEquals(array2[2],array[2]);
	}

	@Test
	public void asListTest() {
		List<Integer> myList = new ArrayList<Integer>();
		List<Integer> myList2 = new ArrayList<Integer>();
		myList.add(1);
		myList.add(2);
		myList.add(3);
		myList2= list.asList();

		assertEquals(myList,myList2);
	}

	@Test
	public void sortTest() {
		Comparator<Integer> comp = (Integer x, Integer y) -> x.compareTo(y);
		assertEquals(list.sort(comp),list.reverse().sort(comp));
	}

	@Test
	public void HeadTest() {
		assertEquals(1, (int)list.head());
	}

	@Test(expected=NoSuchElementException.class)
	public void HeadExceptionTest() {
		emptyList.head();
	}

	@Test
	public void LastTest() {
		assertEquals(3, (int)list.last());
	}

	@Test(expected=NoSuchElementException.class)
	public void LastExceptionTest() {
		emptyList.last();
	}

	@Test
	public void TailTest() {
		ImmutableList<Integer> tail = list.tail();
		assertEquals(2, (int)tail.get(0));
		assertEquals(3, (int)tail.get(1));
		assertEquals(2, tail.size());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void TailExceptionTest() {
		emptyList.tail();
	}
}
