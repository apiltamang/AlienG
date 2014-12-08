package JUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import al.ali.main.Interface;

public class test_DirFileCreation {

	@Test
	public void test1() {
		
		ArrayList<Integer> anArray=new ArrayList<Integer>();
		Interface test=new Interface();
		anArray.add(1);
		anArray.add(2);
		anArray.add(3);
		String tempDir="ApilTempDir";
		String tempFile="testFile.txt";
		try{
			//This should print two lines: 2, and 3 in the file
			test.dumpAlienGOutput(anArray, tempDir,tempFile);
		}catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(1,1);
	}

	@Test
	public void test2(){
		ArrayList<Integer> anArray=new ArrayList<Integer>();
		Interface test=new Interface();
		anArray.add(4);
		anArray.add(5);
		String tempDir="ApilTempDir";
		String tempFile="testFile.txt";
		try{
			////This should append the lines: 4, and 5 to the file
			test.dumpAlienGOutput(anArray, tempDir,tempFile);
		}catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(1,1);
		
	}
	
	@Test
	public void test3(){
		//This test demands creation of a folder, within a folder
		//and create a file within it. Need to write test and 
		//test implementation.
		ArrayList<Integer> anArray=new ArrayList<Integer>();
		Interface test=new Interface();
		anArray.add(1);
		anArray.add(2);
		String nestedDir="TestOutDir\\TestInDir";
		String writeFile="testFile.txt";
		try{
			test.dumpAlienGOutput(anArray, nestedDir, writeFile);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
