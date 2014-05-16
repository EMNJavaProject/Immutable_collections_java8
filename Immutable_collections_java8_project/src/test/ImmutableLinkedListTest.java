package test;

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
	public void GetTest(){
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
	public void AddTest() {
		list = list.concat(4);
		assertEquals(4, (int)list.get(3));
	}
}
