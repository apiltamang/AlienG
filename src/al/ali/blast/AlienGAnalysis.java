package al.ali.blast;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.blast.parser.objects.BlastOutput;
import org.blast.parser.objects.Iteration;

import al.ali.taxonomy.OneHit;
import al.ali.taxonomy.OneHitCounter;

public class AlienGAnalysis {

	public static ArrayList<Integer> getReport(Connection connect,blastObj AlienG_config,HashMap<String,OneHitCounter> hit_counter_obj_list,HashMap<String, OneHit> hit_obj_map,HashMap<String,Zip> child_to_parent_dic,BlastOutput blastOutput ) throws FileNotFoundException, UnsupportedEncodingException, SQLException{
		
		ArrayList<Integer> result = new ArrayList<Integer>();
		//ArrayList<String> list_of_group1 = new ArrayList<String>();
		int hit_no_1 = -1;
		int hit_no_2 = -1;
		boolean belong_hit1_flag = false;
		//Naming ?
		String file1=AlienG_config.getOrderHomeDir()+"apil_only_group1_file.txt";
		String file2=AlienG_config.getOrderHomeDir()+"apil_outfile.txt";
		
		PrintWriter onlyGroup1File_handle = new PrintWriter(file1, "UTF-8");
		PrintWriter outfile_handle = new PrintWriter(file2, "UTF-8");
		System.out.println("<---------STARTING ALIENG ANALYSIS ------------>");
		
		
		int count = 0;
        int hit_no = 0;
        double hit1_evalue = 0;
        double hit2_evalue = 0;
        double hit1_score = 0;
        double hit2_score = 0;
        boolean hit1_flag = false;
        boolean hit2_flag = false;
     	boolean if_filtered_flag = false;
	 //int counter = 0;

		boolean evalue_flag = false;
        boolean score_flag = false;
        
        //These two are already defined as private
        //properties in AlienG_config; so why include 
        //them again?
        //Ans: These values are dynamically calculated. The
        //value provided in the AlienG_config object serve
        //as minimal  cut-off values.
        double AIEvalue;
    	double RatioScore;
		
		int iterations = 0;
		String TITLE = "query_id\tfirst_hit_id\tsecond_hit_id\tfirst_hit_len\tsecond_hit_len\tfirst_hit_coverage\tsecond_hit_coverage\tfirst_hit_Evalue\tsecond_hit_Evalue \tfirst_hit_Score\tsecond_hit_Score\tfirst_hit_lineage\tfirst_hit_species\tfirst_hit_tax_id\tfirst_hit_identities\tfirst_hit_align_len\tsecond_hit_lineage\tsecond_hit_species\tsecond_hit_tax_id\tsecond_hit_identities\tsecond_hit_align_len\tfirst/second_score_ratio\tAI_Evalue\tfirst_annotation\tsecond_annotation\n";

		// didn't care about this         result = parse_conf_file(conf_file,self._name_dic_obj)
		//TABLES_ITERATIONS
		for(int i=0; i<AlienG_config.getNumberOfTables(); i++){
			//System.out.println("this is table" + (i+1));
			outfile_handle.append("Table " + (i+1) + "'s results:\n");
			outfile_handle.write(TITLE + "\n");
			onlyGroup1File_handle.append("Table " + (i+1) + "'s only group 1 results:\n");
			
			//counter = 0;
            ArrayList<String> list_of_seqid = new ArrayList<String>();
         
            int nIterations=blastOutput.getBlastOutput_iterations().size();
            PrintWriter diagnosticWriter=new PrintWriter("Diagnostics.txt","UTF-8");
            //BLASTOUTPUT_ITERATIONS: 
            for (Iteration iteration : blastOutput.getBlastOutput_iterations()){
    			iterations++;
    			System.out.println("Iteration in progress: " + iterations + "/"+nIterations);
    			
    			/*----------------------------------------------
    			 *  Originally in place by Ali. Pretty sure,	!
    			 *  this shouldn't be here.						!
    			 *  
    			if (iterations == 14) {
    				outfile_handle.close();
    				onlyGroup1File_handle.close();
    				return null;
    			}												!
    			*-----------------------------------------------
    			*/
    			//String query_id = iteration.getIteration_query_ID();
    			String query_id = iteration.getIteration_query_def();
    			
    			//System.out.println("start analysis query_id= " + query_id);
                count = 0;
                hit_no = 0;
                hit1_evalue = 0;
                hit2_evalue = 0;
                hit1_score = 0;
                hit2_score = 0;
                hit1_flag = false;
                hit2_flag = false;
                
                //HITS_ITERATION:
                while(true){
                	
                	hit_no++;
                	
                	String hit_key =query_id + "|" + Integer.toString(hit_no);
                	diagnosticWriter.println("hit_key: "+hit_key);
                	
                	if(count >= hit_counter_obj_list.get(query_id).getHit_counter()){
                		if(hit_no == 1){
                			
                			//System.out.println("This query_id is prefiltered");
                			//System.out.println("query_id: " + query_id);
                		}
                		else{
                			//here we just support one table! if the inputs are more than one table we should do something about it!
                			//System.out.println("this is group 2 : " + AlienG_config.getGroup2());
                			if(hit1_flag && !hit2_flag && AlienG_config.getList_group2_taxID().size() != 0){
                				onlyGroup1File_handle.append(query_id + "\n");
                			}
                		}//end if(hit_no==1)
                		
                		break; // out of HITS_ITERATION (and start new BlASTOUTPUT_ITERATIONS)
                	}//end if ( count >= Hit_counter )
                	
                	if(!hit_obj_map.containsKey(hit_key)){
                		//because when load BLAST XML,some hits have already filtered,so here ignore this hits,do not need to consider again
                		
                		/*-----BIG QUESTION -------------------
                		 * WHY not just start with keys from 
                		 * hit_obj_map INSTEAD of iterating
                		 * through all the hits in blastOutput
                		 * 
                		 *------------------------------------*/
                		diagnosticWriter.println(" ");
                		diagnosticWriter.println("  NOT found in hit_obj_map key: "+hit_key);
                		continue; //next item in HITS_ITERATION
                	}//end if (NOT( hit_obj_map.containsKey(hit_key))

                	//hit_key was found in hit_obj_map......
                	//==================Start major analysis and result=================
                	diagnosticWriter.println(" ");
                	diagnosticWriter.println("  FOUND hit_key in hit_obj_map: "+hit_key);
                	if_filtered_flag = false;
                	//String temp = query_id + "|" + Integer.toString(hit_no);
                	//OneHit t = hit_obj_map.get(temp);
                	
                	OneHit foundHit=hit_obj_map.get(hit_key);                	
                	String foundTaxID = foundHit.getTaxId();
                	Zip foundZip = child_to_parent_dic.get(foundTaxID);
                	ArrayList<String> lineage_name = null;
            		ArrayList<String> lineage_taxid = null;
                	
                	
                	lineage_name = foundZip.getChild_to_parent_name();
                	lineage_taxid = foundZip.getChild_to_parent_taxID();
                	
                	
                	//------REFACTORED---------------------------//
                	if_filtered_flag=localBlastHelper.setIfFilteredFlagIsTrue(AlienG_config.getExclusion(),
                			AlienG_config.getExclusionTaxId(), lineage_taxid);

                	//**************************************************************************************************************
                	// A massive if block follows. Concerned with "hit1_flag" and "AlienG_config.Group1()" analysis
                	if(if_filtered_flag)
                	{
            			count++;
            			diagnosticWriter.println(" ");
            			diagnosticWriter.println("    filtered by exclusion test.");
            			continue; //next item in HITS_ITERATION
                	}
            		else if(!hit1_flag) //hit1_flag is set true iff lineage_taxid.contains(an item from alieng_config.group1)
            		{
            			count++;
            			
            			//System.out.println("this is not filtered: start search hit1");
            			//System.out.println("hit1_group_path = " + AlienG_config.getGroup1());
            			
            			/*==== This set of assignment has already been made ======*/
            			foundZip = child_to_parent_dic.get(hit_obj_map.get(
            				  query_id + "|" + Integer.toString(hit_no))
            				  .getTaxId());
            			
                    	lineage_name = foundZip.getChild_to_parent_name();
                    	lineage_taxid = foundZip.getChild_to_parent_taxID();
                    	/*==== END of already assigned set of code ===============*/
            			
                    	//------REFACTORED---------------------------//
                    	if(localBlastHelper.findIFLineageTaxIDinGroup("Group_1",
                    			AlienG_config.getList_group1_taxID(),lineage_taxid))
                    	{
                    		hit_no_1=hit_no;
                    		hit1_flag=true;
                			//hit1_evalue = hit_obj_map.get(query_id + "|" + Integer.toString(hit_no)).getEvalue();
                			//hit1_score = hit_obj_map.get(query_id + "|" + Integer.toString(hit_no)).getScore();
                    		hit1_evalue=hit_obj_map.get(query_id+"|"+Integer.toString(hit_no)).getEvalue();
                    		hit1_score=hit_obj_map.get(query_id+"|"+Integer.toString(hit_no)).getScore();
                    		diagnosticWriter.println(" ");
                    		diagnosticWriter.println("    hit_key found in group1.");
                    		diagnosticWriter.println("    hit1evalue: "+hit1_evalue);
                    	}// end if (lineage_taxID.contains(group1))
                    	
                    	/*===================== EXTREMELY SUSPICIUOUS ABOUT THIS ========*/
                    	/*
                    	 * because: 
                    	 * IF   (I satisfy (hit1_flag==true)), 
                    	 * THEN  I basically analyze the next hit_iteration, 
                    	 * 		 skipping everything else beyond this.
                    	 * ELSE  I move on to analyze the next blastOutput_iteration,
                    	 *       also, effectively skipping everything beyond this.
                    	 * 
                    	 * EITHER WAY: I never proceed from this point of the code!!
                    	 *==============================================================*/
                    	if(hit1_flag)
                    	{
                    		diagnosticWriter.println(" ");
                    		diagnosticWriter.println("      hit1_flag is true.");
                    		diagnosticWriter.println("      surfing through a next hit...");
                    		continue; //next item in HITS_ITERATION                    	
                    	}
                    	else
                    	{
                    		diagnosticWriter.println(" ");
                    		diagnosticWriter.println("      hit1_flag is false.");
                    		diagnosticWriter.println("      beginning a new blastOUTPUT iteration...");
                    		break; //out of HITS_ITERATION, (begin new blastoutput iteration)
                    	}
            		} //if(!hit_flag)
            		
            		else if(hit1_flag && AlienG_config.getGroup2().length() == 0)
            		{
            			// The only way hit1_flag becomes true, is if in the previous
            			// pass through HIT_ITER: while(true) block, the block above
            			// was satisfied. Something else to think about there....
            			
            			//when group2 is null,do not need to check whether there has group2 existed or not
            			
            			if(hit_no_1 == -1)
            			{
            				diagnosticWriter.println(" ");
            				diagnosticWriter.println("WRONG WRONG ");
            				diagnosticWriter.println("query_id: "+query_id);
            				diagnosticWriter.println("hit_no_1: "+hit_no_1);
            			}
            			diagnosticWriter.println(" ");
            			diagnosticWriter.println("      I SHOULD NOT BE HERE SINCE group2.length() != 0.");
            			list_of_seqid.add(query_id + "|" + Integer.toString(hit_no_1));
            			FileWriterHelper.WriteToFile(outfile_handle, query_id + "|" + Integer.toString(hit_no_1),"","","",hit_obj_map, child_to_parent_dic);
            			//counter++;
            			break; //out of HITS_ITERATION (and start one a new BLAST_ITERATION);
            		}
            		else if(hit1_flag && !hit2_flag && AlienG_config.getGroup2().length() != 0)
            		{
            		    belong_hit1_flag = false;
                        count++;
                        //System.out.println("this is not filtered: start search hit2");
                        String newHitKey=query_id + "|" + Integer.toString(hit_no);
                        diagnosticWriter.println(" ");
                        diagnosticWriter.println("        Have a new hit key: "+newHitKey);
                        foundZip = child_to_parent_dic.get(hit_obj_map.get(newHitKey).getTaxId());
                   		lineage_name = foundZip.getChild_to_parent_name();
                   		lineage_taxid = foundZip.getChild_to_parent_taxID();

                   		//----- REFACTORED -------------------------------//
                   		//-- identical code as above ---------------------//
                        belong_hit1_flag=
                        		localBlastHelper.findIFLineageTaxIDinGroup("Group_1",AlienG_config.getList_group1_taxID(), lineage_taxid);
            		}//end if
                	// end of the massive if block
        			//**************************************************************************************************************

                	/*================== EXTREMELY SUSPICIOUS ABOUT THIS AS WELL =========================== */
                	/*               Shares the same anatomy as the suspicious code above                   
                	 * Ali's comment for this block says: "this hit belongs to one of defined group1, ignore"
                	 * suggests that we may have to ignore this hit.					                	 */
                	
                	if (belong_hit1_flag)
                	{
                		//this hit belongs to one of defined group1,ignore
                		diagnosticWriter.println(" ");
                		diagnosticWriter.println("        NewHitKey doesn't work since it belongs in group1.");
                		continue; //next item in HITS_ITERATION
                	}
                	/*==================          END OF SUSPICIOUS CODE         ============================*/
                	
                	//=============================================================================================================//
                	// Start of another IF block (concerned with "hit2_flag" and "AlienG_config.Group2()" analysis.
                	if(AlienG_config.getGroup2().equals("*")){
                		
                		hit2_flag = true;
                		hit_no_2 = hit_no;
                		String newHitKey=query_id + "|" + Integer.toString(hit_no);
                		hit2_evalue = hit_obj_map.get(newHitKey).getEvalue();
                		hit2_score = hit_obj_map.get(newHitKey).getScore();
                		diagnosticWriter.println(" ");
                		diagnosticWriter.println("        hit2_flag is TRUE.");
                		diagnosticWriter.println("        NewHitKey ready for comparison: "+newHitKey);
                		diagnosticWriter.println("        hit2_evalue: "+hit2_evalue+" hit2_score: "+hit2_score);
                	}
                	else
                	{	
                		foundZip = child_to_parent_dic.get(hit_obj_map.get(query_id + "|" + Integer.toString(hit_no)).getTaxId());
                		diagnosticWriter.println("        I SHOULD NOT be here, since my group2 is *. REVISE ");
                   		lineage_name = foundZip.getChild_to_parent_name();
                   		lineage_taxid = foundZip.getChild_to_parent_taxID();
                    	
                     	hit2_flag=
                     			localBlastHelper.findIFLineageTaxIDinGroup("Group_2",AlienG_config.getList_group2_taxID(),lineage_taxid);
                   		
                     	if(hit2_flag)
                     	{
                			hit_no_2 = hit_no;
                			hit2_evalue = hit_obj_map.get(query_id + "|" + Integer.toString(hit_no)).getEvalue();
                			hit2_score = hit_obj_map.get(query_id + "|" + Integer.toString(hit_no)).getScore();
                     		
                     	}
                	} //end if (group2 is equal to "*")																			
                	//============================================================================================================
                	
                	if(hit1_flag && hit2_flag){//find first top hit and second top hit group,continue to check if meet the required condition
                		
						evalue_flag = false;
                        score_flag = false;
                        
                        if(hit1_evalue != 0){
                        	AIEvalue = GetAIEvalue(hit1_evalue,hit2_evalue);
                        }
                        else{
                        	AIEvalue = Double.parseDouble(AlienG_config.getAIEvalue());
                        }
                        
                        
                        if(AIEvalue >= Double.parseDouble(AlienG_config.getAIEvalue())){
                        	//System.out.println("SEQ_ID AIEVALUE ( " + AIEvalue + " ) IS LARGER THAN GIVEN AIEVALUE"); 
                            evalue_flag = true;
                		}
                        
                        /*---- NOTE: -------------------------------------------------
                         * if (lineage_taxid is found in both group1 and group2) then
                         * hit1_score & hit2_score are the same number. So ratio =1.
                         * This is also true if AlienG_config.Group2 = "*".
                         *-----------------------------------------------------------*/
                        RatioScore =GetRatio(hit1_score,hit2_score);//Caculate the this seq_id's RatioScore
                        
                        if (RatioScore >= Double.parseDouble(AlienG_config.getRatioScore())){
            				score_flag = true;
            			}
                        if(evalue_flag && score_flag){
                        	/*-----------------------------------------------------------------------*/
                        	/*         MOST IMPORTANT PART (Selection of a HGT Candidate!!)          */
                        	//System.out.println("This " + query_id +" is selected as HGT candidate");
                        	String oldHitKey=query_id + "|" + Integer.toString(hit_no_1);
                        	String newHitKey=query_id + "|" + Integer.toString(hit_no_2);
                        	diagnosticWriter.println(" ");
                        	diagnosticWriter.println("          everything passed. adding to seq_id hit_key: "+oldHitKey);
                        	list_of_seqid.add(query_id + "|" + Integer.toString(hit_no_1));
                        	FileWriterHelper.WriteToFile(outfile_handle,oldHitKey,newHitKey,String.valueOf(AIEvalue), String.valueOf(RatioScore), hit_obj_map, child_to_parent_dic);
                            //counter++;
                        }
                        else{
                        	//System.out.println("This " +  query_id + " meet first top hit and second top hit,but do not meet the required condition!");
                        	//System.out.println("query_id= " + query_id + " hit_1= " + hit_no_1 + " hit_2= " + hit_no_2 + " AIEvalue= " + AIEvalue + " ratioscore= " + RatioScore);
                        	diagnosticWriter.println(" ");
                        	diagnosticWriter.println("          evalue_flag OR score_flag did NOT pass.");
                        	diagnosticWriter.println("          evalue_flag: "+evalue_flag+" score_flag: "+score_flag);
                        }
                        
                        break;
                	}
                	else{
                		diagnosticWriter.println(" ");
                		diagnosticWriter.println("This " + query_id + " meet first top hit, but do not have second top hit!");
                		//System.out.println("query_id= " + query_id);
                		break;
                	}
                	
                }//end for (HITS_ITERATIONS)
                
            }//end for (BLASTOUTPUT_ITERATIONS)
            diagnosticWriter.close();
		}//end for (TABLES_ITERATIONS)

		outfile_handle.close();
		onlyGroup1File_handle.close();
		
		return result;
	}

	
	public static double GetAIEvalue(double hit1_evalue,double hit2_evalue){
		return Math.log10(hit2_evalue/hit1_evalue);
	}
	
	public static double GetRatio(double num1, double num2){
		if (num2 !=0){
			double result = num1/num2;
			return result;
		}
		else return -1;
	}
	
	
	
}
