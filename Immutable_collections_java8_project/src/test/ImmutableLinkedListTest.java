package test;

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

	@Test(expected=NoSuchElementException.class)
	public void HeadExceptionTest() {
		emptyList.head();
	}

	@Test
	public void AddTest() {
		list = list.concat(4);
		assertEquals(4, (int)list.get(3));
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
		list = list.concat(null);
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
}
