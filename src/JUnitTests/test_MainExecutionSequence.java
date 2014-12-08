package JUnitTests;

import static org.junit.Assert.*;
import ui.ali.*;
import org.junit.Test;
import al.ali.main.Interface;

public class test_MainExecutionSequence {

	@Test
	public void test() {
		try{
			boolean check=false;
			Interface a=new Interface();
			//Config anOrder=Interface.readOneRequest();
			//check=a.executeMainProcess(anOrder);
			assertTrue(check);
		}catch(Exception e){
			e.printStackTrace();;
		}
		
	}

}
