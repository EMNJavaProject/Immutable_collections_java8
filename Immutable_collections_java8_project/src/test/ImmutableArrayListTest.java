package test;

import org.junit.Before;


public class ImmutableArrayListTest extends InductiveIterativeListTest {

	@Before
	public void setUp() {
		super.setUp(new ImmutableArrayListFactory<Integer>());
	}

}
