package al.ali.blast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import al.ali.taxonomy.OneHit;

public class FileWriterHelper {

	public static void WriteToFile(PrintWriter outfile, String first_seq_id, String second_seq_id, String aievalue, String ratioscore, HashMap<String, OneHit> hit_dic_obj,HashMap<String,Zip> child_to_parent_dic_obj){
		boolean proxyDB = true; // just for test
		String string_line = "";
		
		if(second_seq_id == ""){
			string_line = hit_dic_obj.get(first_seq_id).getQueryId() + "\t";
			string_line = string_line + ""+"\t";
			string_line = string_line + hit_dic_obj.get(first_seq_id).getHitId() + "\t";
			string_line = string_line + ""+"\t";
			string_line = string_line + hit_dic_obj.get(first_seq_id).getHitLen() + "\t";
			string_line = string_line + ""+"\t";
			string_line = string_line + hit_dic_obj.get(first_seq_id).getCoverage() + "\t";
			string_line = string_line + ""+"\t";
			string_line = string_line + hit_dic_obj.get(first_seq_id).getEvalue() + "\t";
			string_line = string_line + ""+"\t";
			string_line = string_line + hit_dic_obj.get(first_seq_id).getScore() + "\t";
			string_line = string_line + ""+"\t";

		}
		else{
			string_line = hit_dic_obj.get(first_seq_id).getQueryId() + "\t";
			System.out.println(string_line);
			string_line = string_line + hit_dic_obj.get(first_seq_id).getHitId() + "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(second_seq_id).getHitId() + "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(first_seq_id).getHitLen() + "\t";
			System.out.println(string_line);
			string_line = string_line + hit_dic_obj.get(second_seq_id).getHitLen() + "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(first_seq_id).getCoverage() + "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(second_seq_id).getCoverage() + "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(first_seq_id).getEvalue() + "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(second_seq_id).getEvalue() + "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(first_seq_id).getScore() + "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(second_seq_id).getScore() + "\t";
			System.out.println(string_line);
		}	
		Zip a = child_to_parent_dic_obj.get(hit_dic_obj.get(first_seq_id).getTaxId());
		ArrayList<String> lineage_name = new ArrayList<String>();
		ArrayList<String> lineage_taxid = new ArrayList<String>();
		if (proxyDB){
			ProxyDatabaseHelper.eleven=1;
			lineage_name = ProxyDatabaseHelper.proxyDatabaseName(11);
    		lineage_taxid = ProxyDatabaseHelper.proxyDatabaseTaxID(11);
    	}
    	else{
    		lineage_name = a.getChild_to_parent_name();
    		lineage_taxid = a.getChild_to_parent_taxID();
    	}
		System.out.println("lineage_name " + lineage_name);
    	System.out.println("lineage_taxid" + lineage_taxid);
		/*
		 lineage_name = list(lineage_name)
                lineage_name.reverse()
		string_line = string_line + str(lineage_name[1:]) + '\t'
		 
		 * 
		 * 
		 */
    	Collections.reverse(lineage_name);
    	string_line = string_line + lineage_name.subList(1, lineage_name.size()) + "\t";
		System.out.println(string_line);

		string_line = string_line + hit_dic_obj.get(first_seq_id).getNameWithWhiteSpace()+ "\t";
		System.out.println(string_line);

		string_line = string_line + hit_dic_obj.get(first_seq_id).getTaxId()+ "\t";
		System.out.println(string_line);

		string_line = string_line + hit_dic_obj.get(first_seq_id).getIdentities()+ "\t";
		System.out.println(string_line);

		string_line = string_line + hit_dic_obj.get(first_seq_id).getAlignLen()+ "\t";
		System.out.println(string_line);
		if(second_seq_id == ""){
			string_line = string_line + "" + "\t";
            string_line = string_line + "" + "\t";
            string_line = string_line + "" + "\t";
            string_line = string_line + "" + "\t";
            string_line = string_line + "" + "\t";
		}
		else{
			a = child_to_parent_dic_obj.get(hit_dic_obj.get(second_seq_id).getTaxId());
			if (proxyDB){
				ProxyDatabaseHelper.eleven=2;
				lineage_name = ProxyDatabaseHelper.proxyDatabaseName(11);
	    		lineage_taxid = ProxyDatabaseHelper.proxyDatabaseTaxID(11);
	    	}
	    	else{
	    		lineage_name = a.getChild_to_parent_name();
				lineage_taxid = a.getChild_to_parent_taxID();
	    	}
			System.out.println("lineage_name " + lineage_name);
        	System.out.println("lineage_taxid" + lineage_taxid);
			/*
			 lineage_name = list(lineage_name)
             lineage_name.reverse()
             string_line = string_line + str(lineage_name[1:]) + '\t'
			 * 
			 */
        	Collections.reverse(lineage_name);
        	string_line = string_line + lineage_name.subList(1, lineage_name.size()) + "\t";
			System.out.println(string_line);

			OneHit k = hit_dic_obj.get(second_seq_id);
			string_line = string_line + hit_dic_obj.get(second_seq_id).getNameWithWhiteSpace()+ "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(second_seq_id).getTaxId()+ "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(second_seq_id).getIdentities()+ "\t";
			System.out.println(string_line);

			string_line = string_line + hit_dic_obj.get(second_seq_id).getAlignLen()+ "\t";
			System.out.println(string_line);

			string_line = string_line + ratioscore + "\t";
			System.out.println(string_line);

			string_line = string_line + aievalue + "\t";

		}
		string_line = string_line + hit_dic_obj.get(first_seq_id).getAnnotation() + "\t";
		System.out.println(string_line);

		if(second_seq_id == ""){
			string_line = string_line + "" + "\n";
		}
		else{
			string_line = string_line + hit_dic_obj.get(second_seq_id).getAnnotation() + "\n";
			System.out.println(string_line);

		}
		outfile.append(string_line);
			
		
	}

}
