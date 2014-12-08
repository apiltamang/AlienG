package JUnitTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ali.Interface.JavaRunCommand;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import al.ali.mysql.MySQLAccess;

public class test_internalsProcessBlastOutput {
	private static Connection connect;
	private static MySQLAccess sql;

	@BeforeClass
	public static void startUp()
	{
		sql=new MySQLAccess();
		try {
			connect=sql.newConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void tearDown() throws Exception 
	{
		connect.close();
	}

	//@Test
	public void test_1()
	{
		String hitID="gi|429964089|gb|ELA46087.1|";
		String hitDef="hypothetical protein VCUG_02422 [Vavraia culicis subsp. floridensis]";
		process(hitDef,hitID);
	}
	
	@Test
	public void test_2()
	{
		String hitID="gi|590636767|ref|XP_007028937.1|";
		String hitDef="60S ribosomal protein L6-1 [Theobroma cacao] &gt;gi|508717542|gb|EOY09439.1| 60S ribosomal protein L6-1 [Theobroma cacao]";
		process(hitDef,hitID);
	}
	public void process(String hitDef, String hitID) {
		String title=hitID+" "+hitDef;
		
		
		
		String t[] = title.split("\\]");
		int h = t.length - 2;
		if(h < 0) h += t.length;
		
		String t1[] = t[h].split("\\[");
		String id[] = hitID.split("\\|");
		String hit_id = id[1];//get gi
		String hit_name=t1[1];
		String hit_annotation=t1[0].substring(t1[0].lastIndexOf('|')+1);
		
		for(String aa: t)
		{
			System.out.println("t[]: "+aa);
		}
		System.out.println("-------------");
		
		for(String bb: t1)
		{
			System.out.println("t1[]: "+bb);
		}
		System.out.println("-------------");
		for(String cc: id)
		{
			System.out.println("id[]: "+cc);
		}
		System.out.println("-------------");
		System.out.println("hit_id: "+hit_id);
		System.out.println("-------------");
		System.out.println("hit_annotation: "+hit_annotation);
		System.out.println("-------------");
		String taxID=getTaxID(hit_name);
		System.out.println("Returned TaxID: "+taxID);
		System.out.println("=====================================================");
	
		if(taxID==null)
		{
			processNullTaxID(hitDef);
		}
	}

	public String getTaxID(String hit_name)
	{
		String result;
		try {
			
			result = sql.mapNameToId(connect, hit_name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="foobar";
		}
		return result;
		
	}
	
	public void processNullTaxID(String hit_def) 
	{
		String hit_id=null;
		ArrayList<String> gi_list=new ArrayList<String>();
		
		if(hit_def.contains("gi|"))
		{
			String pos[] = hit_def.split("\\|");
			//======My debugging code ================================================
			System.out.println("--------------->Processing null taxID<-----------"); //		
			
			for(String aa:pos)
			{
				System.out.println("pos[]: "+aa);
			}																		//
			//========================================================================
			if(pos.length >= 2)
			{
				hit_id=pos[1]; //get the gi 
				if(!gi_list.contains(hit_id))
				{
					String sys_cal = "C:\\Program Files\\NCBI\\blast-2.2.28+\\bin\\blastdbcmd -entry " + hit_id + " -db C:\\db\\nr -outfmt \"%S,%T\" -out C:\\db\\tempFile.txt";
					 

					//call sys_call
					JavaRunCommand a = new JavaRunCommand();
					a.run(sys_cal);
					String fileLocation = "C:\\db\\tempFile.txt";
					
					BufferedReader temp_handle = null;
					String temp_line=null;
					try
					{
						temp_handle = new BufferedReader(new FileReader(fileLocation));
						temp_line = temp_handle.readLine();
	
						temp_handle.close();
					}
					catch(Exception e)
					{
						e.printStackTrace();
						assert(false);
					}
					String gi_pos[] = temp_line.split("\\,");
					//======My debugging code ================================================
					System.out.println("------------------------");							//
					
					for(String aa:gi_pos)
					{
						System.out.println("gi_pos[]: "+aa);
					}																		//
					//========================================================================
					
					if(gi_pos.length==2)
					{
						String hit_name=gi_pos[0];
						Integer taxID=Integer.parseInt(gi_pos[1]);
						System.out.println("------------------------------------");					
						System.out.println("hit_name: "+hit_name+" taxID: "+taxID);
						
						//in the real code, this block writes to the hasmap: Hit_obj_Map<String, OneHit>
					}
					else
					{
						if(!gi_list.contains(hit_id))
						{
							gi_list.add(hit_id);
						}
					}//end if (gi_pos.length==2)
					
				}//end if ( ! gi_list.contains(hit_id) )
				
			}//end if (pos.length>=2)
			
			System.out.println("------------->END of processing null taxID<-----------"); //
		}//end if(hit_def.contains("gi")
	}
}
