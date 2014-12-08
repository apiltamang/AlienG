package JUnitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import filehandler.FileHandler;
public class test_copyFiles {

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void test1() {
		String srcDir="jack.sparrow@gmail.com";
		String dstDir="jack.sparrow@gmail.com\\Order-1";
		String file="test-movefile1.txt";
		try{
			FileHandler.moveFile(srcDir, dstDir,file);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test2(){
		String dir="C:\\AlienG\\TaxMappingOrder_14";
		if(!FileHandler.deleteAllFilesInDir(dir))
			System.out.println("Error deleting file..");
	}

}
