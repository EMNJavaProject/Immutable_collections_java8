package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import collections.implementations.ImmutableArrayList;
import collections.implementations.ImmutableLinkedList;
import collections.interfaces.ImmutableList;


public class ImmutableArrayListTest extends ImmutableListTest {

	@Before
	public void setUp() {
		super.setUp(new ImmutableArrayListFactory<Integer>());
	}

}
