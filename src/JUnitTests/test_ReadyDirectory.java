package JUnitTests;
import filehandler.FileHandler;
import static org.junit.Assert.*;

import org.junit.Test;

public class test_ReadyDirectory {

	@Test
	public void test1() throws Exception {
		int val=FileHandler.readyDirectory("TestDir1");
		//Doesn't Already exis, method creates
		//and returns 1
		assertEquals(val,1);
	}
	@Test
	public void test2() throws Exception{
		int val=FileHandler.readyDirectory("TestDir1");
		//Already exists, method returns 0
		assertEquals(val,0);
	}
	
	@Test
	public void test3() throws Exception{
		int val=FileHandler.readyDirectory("OutDir1\\InDir0");
			
			assertEquals(val,1);
	}
	
	@Test 
	public void test4() throws Exception{
		int val=FileHandler.readyDirectory("OutDir1\\InDir0");
			assertEquals(val,0);
	}
}
