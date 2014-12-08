package JUnitTests;

import static org.junit.Assert.*;
import ui.ali.result;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import ui.ali.result;
public class test_getResults {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ArrayList<result> rList=new ArrayList<result>();
		try {
			rList=result.readResult("apil.tamang@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (result i: rList){
			System.out.println(i.getOrdNum());
		}
		
	}

}
