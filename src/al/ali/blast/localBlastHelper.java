package al.ali.blast;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class localBlastHelper {
	
	
	
	public static boolean setIfFilteredFlagIsTrue(String[] exclusion,String[]exclusionTaxID,
			ArrayList<String> lineage_taxid)
	{
		boolean filtered_flag=false;
		//EXCLUSION:
		for(int j=0; j<exclusion.length; j++){
			String each_filtered_taxid = exclusionTaxID[j];
			
			//System.out.println("filtered_taxid =  " + each_filtered_taxid);
			//System.out.println("hit_no= " + hit_no);
			
			if (each_filtered_taxid.equals("No tax_id")){
				//no it isn't, for my sample order, it would be the taxID of (one at a time):
				//Fungi;contaminant;other sequences;crystal structure;Ternary Complex;|pdb|;artificial sequences; etc.
				continue; //Check next item in EXCLUSION
				
			}
			
			if(lineage_taxid.contains(each_filtered_taxid)){
				filtered_flag = true;
				//System.out.println("this is filtered:");
				//System.out.println("defined filtered_taxid= " + each_filtered_taxid );
				//System.out.println("searched lineage= " + child_to_parent_dic_obj.get(hit_dic_obj.get(query_id + "|" + Integer.toString(hit_no)).getTaxId()));
				
				break; //out of EXCLUSION
			}
		}// end for (EXCLUSION)
		
		return filtered_flag;
	}
	
	public static boolean findIFLineageTaxIDinGroup(String comment,ArrayList<String> group1_taxID, ArrayList<String> lineage_taxid)
	{
		int group1_count=0;
		boolean returnVal=false;
		writeLineageTaxIDAndGroupTaxID(comment, group1_taxID, lineage_taxid);
    	//GROUP1_ITER:                    	
    	for(String group1:group1_taxID){
            //System.out.println("group1= " + group1);
            
    		group1_count++;
    		if(!group1.equals("No tax_id")){
    			//a little different than the original version of code!
    			if(lineage_taxid.contains(group1)){
        			returnVal=true;        			
        			break; //out of GROUP1_ITER
    			}
    			else{
    				if(group1_count ==group1_taxID.size()) 
    					break; //out of GROUP1_ITER
    				else 
    					continue;//next item in GROUP_ITER 
    			}
    			
    		}
    		else{
            	//System.out.println("lineage1= " + lineage_taxid );

    			continue; //next item in GROUP1_ITER
    		}
    		
    		
    	} //end for (GROUP1_ITER)
    	
    	return returnVal;
		
	}
	
	public static void writeLineageTaxIDAndGroupTaxID(String comment,ArrayList<String> groupTaxID, ArrayList<String> lineageTaxID)
	{
		try {
			PrintWriter fw=new PrintWriter(new BufferedWriter(new FileWriter("checkVisually.txt",true)));
			
			fw.println("TaxID: "+comment);
			
			for(String str:groupTaxID)
			{
				fw.println("  "+str);
			}//end for
			
			fw.println("Lineage TaxID: ");
			for(String str: lineageTaxID)
			{
				fw.println("  "+str);
			}//end for
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
