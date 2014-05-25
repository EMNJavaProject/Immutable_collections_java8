package test;

import org.junit.Before;


public class ImmutableArrayListTest extends ImmutableListTest {

	@Before
	public void setUp() {
		super.setUp(new ImmutableArrayListFactory<Integer>());
	}

}
