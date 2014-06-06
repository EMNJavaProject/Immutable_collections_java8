package test;

import org.junit.Before;


public class ImmutableReversedArrayListTest extends InductiveIterativeListTest {

	@Before
	public void setUp() {
		super.setUp(new ReversedArrayListFactory<Integer>());
	}

}
