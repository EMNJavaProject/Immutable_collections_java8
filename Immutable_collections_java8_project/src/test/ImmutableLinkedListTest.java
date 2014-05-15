package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import collections.implementations.ImmutableLinkedList;
import collections.interfaces.ImmutableList;

public class ImmutableLinkedListTest {

	private ImmutableList<Integer> list;
	
	@Before
	public void setUp() {
		list = new ImmutableLinkedList<Integer>(1, 2, 3);
	}

	@Test
	public void GetTest(){
		assertEquals(1, list.get(0));
		assertEquals(2, list.get(1));
		assertEquals(3, list.get(2));
		
		assertEquals(0, -1);
		assertEquals(0, 3);
	}
	
	@Test
	public void AddTest(){
		assertEquals(4, list.get(3));
		list=list.concat(4);
		assertEquals(4, list.get(3));
		
	}
	
}
