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
	public void SubListLinkedListTest() {

		// Test that iterators stop at the right last node for sublists
		// (since they all share their structures with a superlist)

		ImmutableList<Integer> subList = list.subList(1, list.size()-1);
		assertEquals(1, subList.size());

		int sizeWhenIterating = 0;
		for (Integer i : subList)
			++sizeWhenIterating;

		assertEquals(subList.size(), sizeWhenIterating);

	}
}
