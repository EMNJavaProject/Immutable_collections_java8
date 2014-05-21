package test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import collections.implementations.ImmutableArrayList;


public class ImmutableArrayListTest {

	private ImmutableArrayList<Integer> list;
	private ImmutableArrayList<Integer> emptyList;
	
	@Before
	public void setUp() {
		list      = new ImmutableArrayList<Integer>(1, 2, 3);
		emptyList = new ImmutableArrayList<Integer>();
		
	}
	
	@Test
	public void Sizetest() {
		assertTrue(list.size() == 3);
		assertTrue(emptyList.size() == 0);
	}

}
