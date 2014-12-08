package JUnitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class test_SanitizeList {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String title="gi|321159577|pdb|2L2E|A Chain A, Solution Nmr Structure Of Myristoylated Ncs1p In Apo Form";
		String exclude="|pdb|";
		
		System.out.println("title.toUpperCase(): "+title.toUpperCase());
		System.out.println("exclude.toUpperCase(): "+exclude.toUpperCase());
		if (title.toUpperCase().contains(exclude.toUpperCase())) 
			assertTrue(true);
		else
			assertTrue(false);
		
	}

}
