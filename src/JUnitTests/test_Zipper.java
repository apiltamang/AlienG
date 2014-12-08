package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import ExtContentPath.UnivPathString;

public class test_Zipper {
/*
 * 
 * 
 */
	@Test
	public void test_zipper(){
		String srcDir="apil.tamang@gmail.com\\blastresults";
		String desFile="apil.tamang@gmail.com\\blastresults.zip";
		al.ali.blast.Zipper.zip(srcDir, desFile);
		assertEquals(1,1);
	}
}
