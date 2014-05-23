package test;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import collections.implementations.ImmutableLinkedList;
import collections.interfaces.ImmutableList;

public class ImmutableLinkedListTest {

	private ImmutableList<Integer> list;
	private ImmutableList<Integer> emptyList;

	@Before
	public void setUp() {
		list      = new ImmutableLinkedList<Integer>(1, 2, 3);
		emptyList = new ImmutableLinkedList<Integer>();
	}

	@Test(expected=NullPointerException.class)
	public void ConstructorExceptionTest() {
		new ImmutableLinkedList<Integer>((Integer[])null);
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
	public void removeTest1() {
		assertEquals(new ImmutableLinkedList<Integer>(2, 3),list.remove(0));
		assertEquals(new ImmutableLinkedList<Integer>(2, 3),list.remove(new Integer(1)));
		assertEquals(new ImmutableLinkedList<Integer>(3),list.remove(new Integer(1),new Integer(2)));
		assertEquals(new ImmutableLinkedList<Integer>(2),list.remove(new ImmutableLinkedList<Integer>(1,3)));
		List<Integer> otherList = new ArrayList<Integer>();
		otherList.add(1);
		otherList.add(3);
		assertEquals(new ImmutableLinkedList<Integer>(2),list.remove(otherList));
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
	public void toarrayTest(){
		Integer[] array= { 1 ,2 ,3 };
		Integer[] array2 =  new Integer[array.length]; 
		array2=list.toArray(array2);
		assertEquals(array2[0],array[0]);
		assertEquals(array2[1],array[1]);
		assertEquals(array2[2],array[2]);
	}

}
