package JUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import ExtContentPath.*;

public class testUnivPathString {

	@Test
	public void test_UnivPathString() {
		String sys_cmd_old= "C:\\Users\\apil\\Dropbox\\ThesisAliSource\\Thesis\\ExternalContent\\blast\\blastall ";
		String sys_cmd_new=UnivPathString.val+"blast\\blastall ";
		
		//assertEquals(sys_cmd_old,sys_cmd_new);
		String hit_id="123";
		String sys_cal = "blastdbcmd -entry " + hit_id + " -db C:\\db\\nr -outfmt \"%S,%T\" -out C:\\db\\tempFile.txt";
		System.out.println(sys_cal);
		
	}

}
