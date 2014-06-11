package test;

import org.junit.Before;

public class ImmutableLinkedListTest extends InductiveIterativeListTest {

	@Before
	public void setUp() {
		super.setUp(new ImmutableLinkedListFactory<Integer>());
	}

}
