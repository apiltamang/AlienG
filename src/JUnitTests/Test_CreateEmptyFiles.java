package JUnitTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class Test_CreateEmptyFiles {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException {
		String root="C:\\AlienG\\taxonomy_mappings_test1\\";
		
		List<String> stringsA= new ArrayList<String>();
		stringsA.add(root+"id_to_name.data.");
		stringsA.add(root+"id_to_node.data.");
		stringsA.add(root+"name_to_id.data.");
		
		List<String> stringsB= new ArrayList<String>();
		stringsB.add("dat");
		stringsB.add("dir");
		
		for(String strA: stringsA)
		{
			for(String strB: stringsB)
			{
				File file=new File(strA+strB);
				if(!file.exists())
				{
					file.createNewFile();
				}
			}
		}
		
	}
	
	@Test
	public void test2() throws IOException {
		String root="C:\\AlienG\\taxonomy_mappings_test1\\";
		
		List<String> stringsA= new ArrayList<String>();
		stringsA.add(root+"child_to_parent.data.");
		stringsA.add(root+"queryHit_counter.data.");
		stringsA.add(root+"queryHit_to_id.data.");
		
		List<String> stringsB= new ArrayList<String>();
		stringsB.add("dat");
		stringsB.add("dir");
		
		for(String strA: stringsA)
		{
			for(String strB: stringsB)
			{
				File file=new File(strA+strB);
				if(!file.exists())
				{
					file.createNewFile();
				}
			}
		}
		
	}

}
