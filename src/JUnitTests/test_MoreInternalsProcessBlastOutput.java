package JUnitTests;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import al.ali.blast.Zip;
import al.ali.mysql.MySQLAccess;
import al.ali.taxonomy.OneHitCounter;
import al.ali.taxonomy.OneHit;

public class test_MoreInternalsProcessBlastOutput {

	static MySQLAccess sql;
	static Connection connect;
	@BeforeClass
	public static void setUp()
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
	public static void cleanUp()
	{
		try {
			connect.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() 
	{
		try
		{
			System.out.println("Deserializing from file.");
			ArrayList<String> Hit_tax_id_list  = (ArrayList<String>)deSerialize("Hit_tax_id_list.txt");
	 		System.out.println("Finished deserialization.");
	
			HashMap<String, Zip> child_to_parent_dic = new HashMap<String, Zip>();
			
			ArrayList<String> child_to_parent_taxID;
			ArrayList<String> child_to_parent_name;;
			
			
			HashMap<String, String> child_to_parent_Map = new HashMap<String, String>();
			ArrayList<String> keys_list = new ArrayList<String>();
			keys_list =	sql.get_id_to_node_dictionaryKeys(connect);
			
			
			//just to make sure we got the keys correctly
			if(keys_list.size() == 0){
				System.out.println("there is no key in the keys_list which means something is wrong");
				return;
			}
			
			int numRemoved=0;
			String child_tax_id = null;
			while(Hit_tax_id_list.size() > 0){
				child_tax_id = Hit_tax_id_list.get(0);
				
				if(child_to_parent_Map.containsKey(child_tax_id) || child_to_parent_dic.containsKey(child_tax_id)){ // there was another condition related to child_to_parent_dictionary
					Hit_tax_id_list.remove(0);
					numRemoved++;
					continue;
				}
				if(keys_list.contains(child_tax_id)){
					child_to_parent_taxID=new ArrayList<String>();
					child_to_parent_name=new ArrayList<String>();
					child_to_parent_taxID.add(child_tax_id);
					child_to_parent_name.add(sql.mapIdToName(connect, child_tax_id));
					
					while(true){
						String parent_tax_id = sql.getParent_IdToNode(connect, child_tax_id);
						if(parent_tax_id.equals("1")){
							break;
						}
						else{
							if(keys_list.contains(parent_tax_id)){
								child_to_parent_taxID.add(parent_tax_id);
								String name = sql.mapIdToName(connect, parent_tax_id);
								child_to_parent_name.add(name);
								
							}
							child_tax_id = parent_tax_id;
						}
					}
					child_to_parent_dic.put(Hit_tax_id_list.get(0), new Zip(child_to_parent_name, child_to_parent_taxID));
					Hit_tax_id_list.remove(0);
					numRemoved++;
				}
				else{
					child_to_parent_taxID=new ArrayList<String>();
					child_to_parent_name=new ArrayList<String>();
					child_to_parent_taxID.add(child_tax_id);
					child_to_parent_name.add("could not find lineage");
					child_to_parent_dic.put(Hit_tax_id_list.get(0), new Zip(child_to_parent_name, child_to_parent_taxID));
					Hit_tax_id_list.remove(0);
					numRemoved++;
				}
				
				if(numRemoved%1000==0)
				{
					System.out.println("Number removed: "+numRemoved);
				}
			}
			
			sql.close(connect);
			
			PrintWriter writer=new PrintWriter("child_to_parent_dic_View.txt","UTF-8");
			writer.println("HashMap<String,Zip>");
			System.out.println("Writing to file...");
			for(String key:child_to_parent_dic.keySet())
			{
				writer.println("Key:   "+key);
				writer.println("Value: "+child_to_parent_dic.get(key).toString());
			}
			System.out.println("Finished writing to file.");
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
			
	}

	public static Object deSerialize(String serilizedObject) throws FileNotFoundException, IOException, ClassNotFoundException {
	    FileInputStream fis = new FileInputStream(serilizedObject);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    return ois.readObject();
	}

}
