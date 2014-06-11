package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import collections.implementations.DoubleLinkedListProxy;

public class DoubleLinkedListProxyTest {

	DoubleLinkedListProxy<Integer> list;
	DoubleLinkedListProxy<Integer> nil;

	@Before
	public void setUp() {
		list = new DoubleLinkedListProxy<Integer>();
		nil  = list.nil();
	}

	@Test
	public void testNil() {
		assertTrue(nil.isEmpty());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(list.isEmpty());
		assertFalse(list.cons(1).isEmpty());
	}

	// @Test(expected=UnsupportedOperationException.class)
	public void testTailException() { // TODO: should throw an exception
		// nil.tail();
		assertEquals(nil, nil.tail());
	}

	@Test
	public void testTail() {
		list = list.cons(1);
		list = list.cons(2);
		assertEquals(nil.cons(1), list.tail());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testHeadException() {
		list.head();
	}

	@Test
	public void testHead() {
		list = list.cons(1).cons(2);
		assertEquals(2, (int)list.head());
	}

	@Test
	public void testCons() {
		list = list.cons(3);
		list = list.cons(2);
		list = list.cons(1);

		assertEquals(1, (int)list.head()); list = list.tail();
		assertEquals(2, (int)list.head()); list = list.tail();
		assertEquals(3, (int)list.head());
	}

	@Test
	public void testConcat() {
		assertEquals(nil, list.concat(list));

		DoubleLinkedListProxy<Integer> expected = nil.cons(2).cons(1);
		DoubleLinkedListProxy<Integer> one = new DoubleLinkedListProxy<Integer>().cons(1);
		DoubleLinkedListProxy<Integer> two = new DoubleLinkedListProxy<Integer>().cons(2);
		assertEquals(expected, one.concat(two));
	}

	@Test
	public void testReverse() {
		assertEquals(nil, list.reverse());

		DoubleLinkedListProxy<Integer> expected = new DoubleLinkedListProxy<Integer>();
		expected = expected.cons(3).cons(2).cons(1);

		list = nil.cons(1).cons(2).cons(3);
		assertEquals(expected, list.reverse());
	}

	@Test
	public void testMap() {
		list = list.cons(1).cons(2).cons(3);

		DoubleLinkedListProxy<Integer> expected = new DoubleLinkedListProxy<Integer>();
		expected = expected.cons(2).cons(4).cons(6);

		assertEquals(expected, list.map((Integer x) -> x * 2));
	}

	@Test
	public void testEndoMap() {
		list = list.cons(1).cons(2).cons(3);

		DoubleLinkedListProxy<Integer> expected = new DoubleLinkedListProxy<Integer>();
		expected = expected.cons(2).cons(4).cons(6);

		assertEquals(expected, list.endoMap((Integer x) -> x * 2));
	}

	@Test
	public void testEquals() {
		assertEquals(nil, list);

		list = list.cons(3).cons(2).cons(1);
		DoubleLinkedListProxy<Integer> expected = new DoubleLinkedListProxy<Integer>();
		expected = expected.cons(3).cons(2);
		assertFalse(expected.equals(list));

		expected = expected.cons(1);
		assertEquals(expected, list);
	}
}
