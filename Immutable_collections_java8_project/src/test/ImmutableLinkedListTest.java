package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import collections.implementations.ImmutableLinkedList;
import collections.interfaces.ImmutableList;

public class ImmutableLinkedListTest extends ImmutableListTest {

	@Before
	public void setUp() {
		super.setUp(new ImmutableLinkedListFactory<Integer>());
	}

	@Test
	public void HeadTest() {
		assertEquals(1, (int)((ImmutableLinkedList<Integer>)list).head());
	}

	@Test(expected=NoSuchElementException.class)
	public void HeadExceptionTest() {
		((ImmutableLinkedList<Integer>)emptyList).head();
	}

	@Test
	public void LastTest() {
		assertEquals(3, (int)((ImmutableLinkedList<Integer>)list).last());
	}

	@Test(expected=NoSuchElementException.class)
	public void LastExceptionTest() {
		((ImmutableLinkedList<Integer>)emptyList).last();
	}

	@Test
	public void TailTest() {
		ImmutableList<Integer> tail = ((ImmutableLinkedList<Integer>)list).tail();
		assertEquals(2, (int)tail.get(0));
		assertEquals(3, (int)tail.get(1));
		assertEquals(2, tail.size());
	}

	@Test(expected=UnsupportedOperationException.class)
	public void TailExceptionTest() {
		((ImmutableLinkedList<Integer>)emptyList).tail();
	}

}
