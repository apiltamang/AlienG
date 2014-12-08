package JUnitTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import al.ali.blast.localBlast;
import al.ali.taxonomy.*;

import org.junit.After;
import org.junit.Test;

public class test_viewProcessBlastArtifact {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws FileNotFoundException, ClassNotFoundException, IOException {

		HashMap<String, OneHitCounter> Hit_counter_obj_list = (HashMap<String, OneHitCounter>)localBlast.deSerialize("Hit_counter_obj_list_serialization.txt");
		ArrayList<String> Hit_tax_id_list  = (ArrayList<String>)localBlast.deSerialize("Hit_tax_id_list.txt");
 		HashMap<String, OneHit> Hit_obj_Map = (HashMap<String, OneHit>)localBlast.deSerialize("Hit_obj_Map.txt");
		
 		//write content to file
 		PrintWriter writer=new PrintWriter("Hit_Counter_Obj_List_View.txt","UTF-8");
 		writer.println("<String, OneHitCounter>");
 		for(String key: Hit_counter_obj_list.keySet())
 		{
 			
 			writer.println("-----------------------------------------");
 			writer.println("key:    "+key);
 			writer.println("value:  "+Hit_counter_obj_list.get(key).toString());
 			
 		}//end for
 		writer.close();
 		
 		//write content to file
 		PrintWriter writer1=new PrintWriter("Hit_obj_Map_View.txt","UTF-8");
 		writer1.println("<String, OneHit>");
 		for(String key: Hit_obj_Map.keySet())
 		{
 			writer1.println("-----------------------------------------");
 			writer1.println("key:    "+key);
 			writer1.println("value:  "+Hit_obj_Map.get(key).toString());
 		}
 		writer1.close();
 		
 		PrintWriter writer2=new PrintWriter("Hit_tax_id_list_View.txt","UTF-8");
 		for(String item:Hit_tax_id_list)
 		{
 			writer2.println(item);
 		}
 		writer2.close();
	}

}
