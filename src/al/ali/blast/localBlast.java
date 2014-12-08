package al.ali.blast;

import ExtContentPath.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import ui.ali.Config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.blast.parser.main.XMLParser;
import org.blast.parser.objects.BlastOutput;
import org.blast.parser.objects.Hit;
import org.blast.parser.objects.Hsp;
import org.blast.parser.objects.Iteration;

import ui.ali.Config;

import com.ali.Interface.JavaRunCommand;











//import al.ali.main.serialize;
import al.ali.mysql.MySQLAccess;
import al.ali.taxonomy.OneHit;
import al.ali.taxonomy.OneHitCounter;
public class localBlast {
	
	String program = "blastp";
	int alignment = 100;
	String expectation = "1e-5";
	public String doBlast(blastObj bo){
		/*
		 * Needs complete overhaul to possibly
		 * a) use new BLAST+ interface
		 * b) delegate this task to the cluster if it is possible
		 */
		String sys_cmd =UnivPathString.val+"blast\\blastall"+ 
					    
					    " -d " + bo.getBlastdb()+ 
					    " -i " + bo.getInfile() + 
					    " "    + bo.getBlastparam()+
					    " -o " + bo.getBlastoutput();
		
		
		//System.out.println("sys_cmd: "+sys_cmd);
		
		//String sys_cmd = "C:\\blast\\blastall"+ " -p " + this.program+ " -d " + bo.getBlastdb()+" -i " + bo.getInfile() + " -b "+ this.alignment +" -e "+ this.expectation +" -m 7 -o " + bo.getBlastoutput();
		//String sys_cmd = "C:\\AlienGFiles\\blast\\blastall -p blastp -d C:\\AlienGFiles\\nr\\nr -i C:\\AlienGFiles\\Antonospora_locustae.fas_new -b 100 -e 1e-5 -m 7 -o C:\\AlienGFiles\\blastOutput.xml";
		JavaRunCommand s = new JavaRunCommand();
	
		
		/* TODO: Undo comment while running a real job
		 * s.run(sys_cmd);
		 */
		
		//System.out.println("Blast Output Absolute File Location: "+bo.getBlastoutput());
		
		
		return bo.getBlastoutput();
	}
	
	public ArrayList<Integer> ProcessBlastOutput(String BlastXMLoutput, blastObj AlienG_config) throws Exception{
		
		
		ArrayList<Integer> Results = new ArrayList<Integer>();

		MySQLAccess sql = new MySQLAccess();
	    Connection connect=null;
	    connect = sql.newConnection();
		
		
		//reading the blast output
		XMLParser parser = new XMLParser();
		
		BlastOutput blastOutput = null;
		blastOutput = parser.parseXML(BlastXMLoutput);
		
		//skipBlastOutputProcessing determines if we should perform
		//a rather lengthy process of processing the blast output,
		//or if we can just read it from a set of serialized files.
		boolean skipBlastOutputProcessing=false;
		boolean skipEvalChildToParentDic=false;
		
		boolean skip_flag;
		int hsp_count;
		int hsp_len;
		float coverage;
		String hit_lineage = "";
		
		PrintWriter exceptionTracer=new PrintWriter("Exception-Tracer.python.txt","UTF-8");
		
		ArrayList<String> gi_list = new ArrayList<String>();
		
		
		HashMap<String,String> unUsed=new HashMap<String,String>();
 		HashMap<String, OneHit> Hit_obj_Map = new HashMap<String, OneHit>();
		HashMap<String, OneHitCounter> Hit_counter_obj_list = new HashMap<String, OneHitCounter>();
		ArrayList<String> Hit_tax_id_list = new ArrayList<String>();//Create tax_id list for every hit of different seq_ID
		
		if(!skipBlastOutputProcessing)
		{
			int iterationsNum=blastOutput.getBlastOutput_iterations().size();
			
			int iterationCompleted=0;
			for (Iteration iteration : blastOutput.getBlastOutput_iterations()){
				String iterTrace="C:\\Iterations\\IterationTrace-"+(iterationCompleted+1)+".python.txt";
				PrintWriter tracer=new PrintWriter(iterTrace,"UTF-8");
				
				/*
				 * Print exclusion list
				 */
				for(String exclude:AlienG_config.getExclusion())
				{
					tracer.write("exclusion taxids: "+exclude+"\n");
				}
				tracer.println("Iteration_num: "+(iterationCompleted+1));
				System.out.printf("Processing iteration number: %d of %d\n", (++iterationCompleted),iterationsNum);
				
	
				try{
				    //String query_id = iteration.getIteration_query_ID();
					String query_id = iteration.getIteration_query_def();
					tracer.println("Iteration_id: "+query_id);
					
					int query_len = iteration.getIteration_query_len();
					int hit_no = 0;
					int query_hit_count = 0;
					if(iteration.getIteration_hits() == null)
					{
						//move to next iteration
						tracer.println("No hits. Next Iteration...");
						continue;
					}
					
					
					int numHits=iteration.getIteration_hits().size();
					int alignNum=0;
					for (Hit alignment : iteration.getIteration_hits()){
						try{
							alignNum++;
							
							tracer.println("    Alignment_num: "+alignNum);
						
							hit_no++;
							if( (hit_no%25) ==0)
							{
								System.out.printf("----> done hits: %d of %d\n", hit_no,numHits);
							}
							skip_flag = false;
							hsp_count = 0;
							int hsp_iterator=0;
							for(Hsp hsp : alignment.getHit_hsps()){
								try{
									if(hsp_count == 0){//Only get the first hsp
										hsp_len = hsp.getHsp_align_len();
										coverage = ((float)hsp_len/(float)query_len);
										
										tracer.println("        hsp_iterator: "+(hsp_iterator++));
										//initial filtering
										if((Double.compare(hsp.getHsp_evalue(), AlienG_config.getEvalueMax()) < 0 ) 
												&& ( Double.compare(hsp.getHsp_bit_score(), AlienG_config.getScoreMin()) > 0 )
													&& (Float.compare(coverage, AlienG_config.getCoverage()) > 0))
										{
											
											tracer.write("        Hit passed first criteria test.\n");
											String title = alignment.getHit_id() + " "+ alignment.getHit_def();
											 /* 
											  * and include only those species
											  * d) ! (name.contains(exclusion_list))
											  * 
											  */
											if(AlienG_config.getExclusion() != null){ 
												for(String excluded: AlienG_config.getExclusion()){
													 if(title.toUpperCase().contains(excluded.toUpperCase())){
														 tracer.println("        Alignment.title found in exclusion list. Breaking analysis...");
														 tracer.println("	      alignment.hit_def:  "+title.toUpperCase());
														 tracer.println("        matching exclusion:  "+excluded.toUpperCase());
														 skip_flag = true;
														 break;
													 }
												 }
											}
											if (skip_flag == true){
												//System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + " filtered");
												continue;
											}
											
											tracer.write("        Hit passed second criteria test\n");
											//#Rule out:Hit without species name,Hit_def:No definition line found
											// i don't really see any case in which this condition wouldn't pass
											if(title.contains("No definition line found") == false){  
												//System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + "not filtered");
												//System.out.println("title=  " + title);
												tracer.println("        Alignment title has definition");
												String t[] = title.split("\\]");
												tracer.println("        title: "+title);
												tracer.write(  "        t ( title.split(']') )gives: ");
												for (String st: t)
												{
													tracer.write(st+", ");
												}
												tracer.println();
												int h = t.length - 2;
											    if(h < 0) 
											    	h += t.length;
											   
												String t1[] = t[h].split("\\[");
												tracer.write("        t1 ( t[len(t)-2.split('[') ) gives: ");
												for (String st: t1)
												{
													tracer.write(st+", ");
												}
												
												tracer.println();
												String id[] = alignment.getHit_id().split("\\|");
												/*
												tracer.write("        id (alignment.hit_id.split('|)) gives: ");
												for(String st: id)
												{
													tracer.write(st+", ");
												}
												tracer.println();
												*/
												
												
												String hit_id = id[1];//get gi
												tracer.println("        hit_id: "+hit_id);
												boolean if_exist_species_name = false;
												boolean if_species_name_match = false;
												
												if(t1.length == 2){
													if_exist_species_name = true;
													String hit_name = t1[1];
													//String a = "Vavraia culicis 'floridensis'";
				                                    int hit_len = alignment.getHit_len();
				                                    //strip ...
				                                    String hit_annotation = t1[0].substring(t1[0].lastIndexOf('|')+1);
				                                   // String[] hit_annotation = temp.split(" ");
				                                    
				                                    String taxID = sql.mapNameToId(connect,hit_name);
				                                    //taxID could be null. need to be checked
				                                    //System.out.println("sign [] happend !!!!!!");
													tracer.println("        exists species name.");
													tracer.println("        hit_name:  "+hit_name);
													tracer.println("        hit_len:   "+hit_len);
													tracer.println("        hit_anno:  "+hit_annotation.trim());
													if(taxID==null)
														tracer.println("        hit_taxID: "+"No tax_id");
													else
														tracer.println("        hit_taxID: "+taxID);
				                                    //System.out.println("taxID= " + taxID);
													if(taxID != null){//if species do not have tax_id,no record
														 if_species_name_match = true;
					                                     String hit_tax_id = taxID;
					                                     Hit_tax_id_list.add(taxID);
					                                     double hit_score = hsp.getHsp_bit_score();
					                                     double hit_evalue = hsp.getHsp_evalue();
					                                     tracer.println("        taxID is not null.Appending to Hit_obj_list.");
					                                     tracer.println("        hit_score:    "+hit_score);
					                                     String printEVal=String.format("%.5E",hit_evalue);
					                                     tracer.println("        hit_evalue:   "+printEVal);
					                                     int hit_identity = hsp.getHsp_identity();
					                                     int hit_align_len = hsp.getHsp_align_len();
					                                     // not sure if these are actually working or not!
					                                     String Key = query_id + "|" + Integer.toString(hit_no);
					                                     /*
					                                      * if(taxID !=null)
					                                      * 	this block writes a new item into
					                                      * 	a) Hit_obj_Map
					                                      * 	b) Hit_tax_id_list
					                                      */
					                                     Hit_obj_Map.put(Key, new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len));
					                                     //Hit_obj_list.add(new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len));
					                                     query_hit_count++;
													}//end if(taxID !=null)
													
													 
												}//end if (t1.length==2) 
												/*==========================================
												 * if(t1.length==2)
												 * {
												 * 		if_exist_species_name=true
												 * }
												 * else
												 * {
												 * 		if_exist_species_name=false
												 * }
												 * --------------------------------
												 * if(taxID !=null)
												 * {
												 * 		if_species_name_match=true
												 * }
												 * else
												 * {
												 * 		if_species_name_match=false
												 * }
												 *========================================= 
												 */
												
												/*
												 * The if-test below is equivalent to
												 * (taxID !=null ) AND (t1.length==2),
												 * So,
												 * if (taxID == null),
												 *  	below block is always executed
												 *  
												 *  ----------------------------
												 *  but it could be that 
												 *  
												 *  	if(taxID !=null) 
												 *  		AND 
												 *  	t1.length != 2,
												 *  
												 *  	in which case,
												 *  	- Hit_tax_id_list.add(...), and
												 *  	- Hit_obj_Map.put(...)
												 *  
												 *  	could be called twice for the same hit under analysis.
												 *      This is most probably erroneous.
												 */
												if(if_exist_species_name == false || if_species_name_match == false){
													// this is test: String hit_def = alignment.getHit_id();
													String hit_def = alignment.getHit_def();
													tracer.println("        neither species name exists or matches: "+hit_def);
													//System.out.println("hit_def= " + alignment.getHit_def() );
													if(hit_def.contains("gi|"))
													{//find gi in hit_def
														String pos[] = hit_def.split("\\|");
														tracer.write("        search for gi.\n");
														if(pos.length >= 2)
														{ // exist gi
															tracer.println("        hit_def contains gi.");
															hit_id = pos[1];//get gi
															if(!gi_list.contains(hit_id))
															{
																//sys_call                                                                                                                                                                                                                                                                                                                                                                                                            
																String sys_cal = "blastdbcmd -entry " + hit_id + " -db C:\\AlienG\\blast_database\\nr -outfmt \"%S,%T\" -out C:\\AlienG\\tempFile.txt";
																tracer.write("        called blastdbcmd for entry: "+hit_id+"\n");
																//System.out.println("sys_cal= " + sys_cal );
																//call sys_call
																JavaRunCommand a = new JavaRunCommand();
																a.run(sys_cal);
																String fileLocation = "C:\\AlienG\\tempFile.txt";
																
																BufferedReader temp_handle = null;										 
																temp_handle = new BufferedReader(new FileReader(fileLocation));
																String temp_line = temp_handle.readLine();
																String foobar;
																try
																{
																	//split ..
																	//we need strips here
																	String gi_pos[] = temp_line.split("\\,");
																	
																	if(gi_pos.length == 2)
																	{
																		foobar="-->len(this)==2 True";
																		String hit_name = gi_pos[0];
																		Integer taxID = Integer.parseInt(gi_pos[1]);
																		//System.out.println("gi: " + hit_id + " ,hit_name: "+ hit_name + " ,tax_id: " + taxID);
																		int hit_len = alignment.getHit_len();
																		String hit_annotation = "No species name in BLAST output, so get tax_id from gi";
																		String hit_tax_id = taxID.toString();
																		Hit_tax_id_list.add(hit_tax_id);
																		double hit_score = hsp.getHsp_bit_score();
																		double hit_evalue = hsp.getHsp_evalue();
																		int hit_identity = hsp.getHsp_identity();
																		int hit_align_len = hsp.getHsp_align_len();
																		
																		tracer.write("        gi: "+hit_id+", hit_name: "+hit_name+", tax_id: "+taxID+"\n");
																		//OneHit a = new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len);
																		//Hit_obj_list.add(new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len));
																		String Key = query_id + "|" + Integer.toString(hit_no);
									                                    /*
									                                     * If (taxID == null)
									                                     * 		then this block (could potentially) write into
									                                     * 		a) Hit_obj_Map, and
									                                     * 		b) Hit_tax_id_list
									                                     */
																		Hit_obj_Map.put(Key, new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len));
																		query_hit_count++;	
																	}
																	else
																	{//using gi could not find tax_id
																		foobar="-->len(this)==2 False";
																		tracer.write("        could not find tax_id using gi: "+hit_id+"\n");
																		if(!gi_list.contains(hit_id)){
																			gi_list.add(hit_id);	
																			tracer.write("        gi_list.append( "+hit_id+" )\n");
																		}
																		//System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + ", we could not find species name and gi,so ignore this record!");
																	}
																}//end try
																catch(NullPointerException e)
																{
																	foobar="-->len(this)==2 False";
																	tracer.write("        could not find tax_id using gi: "+hit_id+"\n");
																	if(!gi_list.contains(hit_id)){
																		gi_list.add(hit_id);	
																		tracer.write("        gi_list.append( "+hit_id+" )\n");
																	}
																	//System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + ", we could not find species name and gi,so ignore this record!");
																}//end try/except block
																unUsed.put(hit_id, temp_line+foobar);
																temp_handle.close();

															}//end if(!gi.contains(hit_id)
														}//end if(pos.length>2)
														
													}
													else{//could not find gi
														//System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + " we could not find species name and gi,so ignore this record!");
														tracer.println("        could not find gi.");
														tracer.println("        query_id: "+query_id);
														tracer.println("        hit_no:   "+hit_no);
													}
												}
												
											}
										}
										else
										{
											//System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + " cut off by Evalue_threshold,ScoreMin,coverage");
											tracer.write("        Cut off by first criteria test.\n");
											tracer.write("        Sequence: "+query_id+" Hit_no: "+hit_no+"\n");
										}
										hsp_count++;
											
									}//end if(hsp_count==0) [only perform analysis with the first hsp element]
								
								}
								catch(Exception e)
								{
									exceptionTracer.write("Exception occured while iterating hit_hsps. Printing stack-trace..\n");
									e.printStackTrace(exceptionTracer);
									continue;
								}
							}//end for, iterate hit_hsps
							
						}
						catch(Exception e)
						{
							exceptionTracer.write("Exception occured while iterating hits. Print trace...\n");
							e.printStackTrace(exceptionTracer);
							continue;
						}
					}//end for, iterate hits
					
					//Hit_counter_obj_list.add(qnew OneHitCounter(query_id, query_hit_count));
					Hit_counter_obj_list.put(query_id, new OneHitCounter(query_id, query_hit_count));
					
				}
				catch(Exception e)
				{
					exceptionTracer.write("Exception occured while iterating blast iteration. Printing trace...\n");
					e.printStackTrace(exceptionTracer);
					continue;
				}
				
				tracer.close();
	
			}//end for; iterate iterations
		
			
			
			System.out.println("Hits Done!");
			
			System.out.println("Start creating lineage information.....");
			
			//----------------------------------------------------------------------
			/*
			 * When running a job, comment out the block of 'serialize' and 
			 * 'unserialize' statements. The variables: 
			 * - Hit_counter_obj_list,
			 * - Hit_tax_id_list, and
			 * - Hit_obj_Map
			 * 
			 * needs to be propagated after the statements above. Now, these variables
			 * have been serialized into a respective file, and instantiated from these files.
			 * 
			*/ 
			//just for serialization
			serialize("Hit_counter_obj_list_serialized.txt", Hit_counter_obj_list);
			serialize("Hit_tax_id_list_serialized.txt", Hit_tax_id_list);
			serialize("Hit_obj_Map_serialized.txt", Hit_obj_Map);
		}
		else
		{
			/*
			 * --Uncomment the following block of code if you want to read from a serialized file.
			 */
			System.out.println("Populating Hit_counter_obj_list...");
			Hit_counter_obj_list = (HashMap<String, OneHitCounter>)deSerialize("Hit_counter_obj_list_serialized.txt");
			System.out.println("Populating Hit_tax_id_list...");
			Hit_tax_id_list  = (ArrayList<String>)deSerialize("Hit_tax_id_list_serialized.txt");
			System.out.println("Populating Hit_obj_Map...");
			Hit_obj_Map = (HashMap<String, OneHit>)deSerialize("Hit_obj_Map_serialized.txt");
			
		}

		/* Print all the times that blastdbcmd was invoked, and the results obtained*/
		PrintWriter writer=new PrintWriter("blastdbcmd_outputs.txt","UTF-8");
		for(String key:unUsed.keySet())
		{
			writer.write(key+"-->"+unUsed.get(key));
		}
		writer.close();
		//same as the getLineage in original AlienG
		//used for getting lineage information for a list of tax_id(s),the lineage information represented as a list of species name with tax_id
		HashMap<String, Zip> child_to_parent_dic = null;
		
		ArrayList<String> child_to_parent_taxID;
		ArrayList<String> child_to_parent_name;;
		
		
		HashMap<String, String> child_to_parent_Map = new HashMap<String, String>();
		ArrayList<String> keys_list = new ArrayList<String>();
		keys_list =	sql.get_id_to_node_dictionaryKeys(connect);
		
		
		//just to make sure we got the keys correctly
		if(keys_list.size() == 0){
			System.out.println("there is no key in the keys_list which means something is wrong");
			return null;
		}
		
		String child_tax_id = null;
		int todoSize=Hit_tax_id_list.size();
		int removed=0; 
		
		PrintWriter tracer=new PrintWriter("IterationTrace_keys_list_JAVA.txt","UTF-8");
		if(!skipEvalChildToParentDic)
		{
			child_to_parent_dic=new HashMap<String,Zip>();
			while(Hit_tax_id_list.size() > 0){
				if(removed%5000==0)
					System.out.println("Processing item: "+removed+" of "+todoSize);
				child_tax_id = Hit_tax_id_list.get(0);
				tracer.write("child_tax_id: "+child_tax_id+"\n");
				if(child_to_parent_Map.containsKey(child_tax_id) || child_to_parent_dic.containsKey(child_tax_id)){ // there was another condition related to child_to_parent_dictionary
					tracer.write("    Passed first elimination test.\n");
					tracer.write("    Moving on to next element in Hit_tax_id_list.\n");
					Hit_tax_id_list.remove(0);
					removed++;
					continue;
				}
				if(keys_list.contains(child_tax_id)){
					child_to_parent_taxID=new ArrayList<String>();
					child_to_parent_name=new ArrayList<String>();
					tracer.write("    Append to child_to_parent_taxID: "+child_tax_id+"\n");
					child_to_parent_taxID.add(child_tax_id);
					String tempVal=sql.mapIdToName(connect,child_tax_id);
					tracer.write("    Append to child_to_parent_name: "+tempVal+"\n");
					child_to_parent_name.add(tempVal);
					
					while(true){
						String parent_tax_id = sql.getParent_IdToNode(connect, child_tax_id);
						tracer.write("        Get parent_tax_id: "+parent_tax_id+"\n");
						if(parent_tax_id.equals("1")){
							tracer.write("            parent_tax_id is root. Breaking...\n");
							break;
						}
						else{
							if(keys_list.contains(parent_tax_id)){
								child_to_parent_taxID.add(parent_tax_id);
								String name = sql.mapIdToName(connect, parent_tax_id);
								tracer.write("            parent_tax_id is in keys_list.\n");
								tracer.write("            Append to child_to_parent_taxID: "+parent_tax_id+"\n");
								tracer.write("            Append to child_to_parent_name: "+name+"\n");
								
								child_to_parent_name.add(name);
								
							}
							child_tax_id = parent_tax_id;
						}
					}
					tracer.write("    Append to child_to_parent_dic: Key: "+Hit_tax_id_list.get(0)+"\n");
					child_to_parent_dic.put(Hit_tax_id_list.get(0), new Zip(child_to_parent_name, child_to_parent_taxID));
					Hit_tax_id_list.remove(0);
					removed++;
				}
				else{
					child_to_parent_taxID=new ArrayList<String>();
					child_to_parent_name=new ArrayList<String>();
					child_to_parent_taxID.add(child_tax_id);
					child_to_parent_name.add("could not find lineage");
					child_to_parent_dic.put(Hit_tax_id_list.get(0), new Zip(child_to_parent_name, child_to_parent_taxID));
					tracer.write("    Append to child_to_parent_taxID: "+child_tax_id+"\n");
					tracer.write("    This taxID not in keys_list\n");
					Hit_tax_id_list.remove(0);
					removed++;
				}
				
				
			}
			
			serialize("Child_to_parent_dic_serialized.txt",child_to_parent_dic);
			
		}
		else
		{
			System.out.println("Hydrating child_to_parent_dic from serialized file...");
			child_to_parent_dic=(HashMap<String,Zip>)deSerialize("Child_to_parent_dic_serialized.txt");
			System.out.println("Done!");
		}
		
		tracer.close();
		System.out.println("Linking children to parents done!");
		
		System.out.println("Create lineage information done successfully!");
		
		
		//get results
		Results = AlienGAnalysis.getReport(connect,AlienG_config,Hit_counter_obj_list,Hit_obj_Map,child_to_parent_dic,blastOutput);
		sql.close(connect);
		
		
		return Results;
	} 
	
	@Override
	public String toString()
	{
		String str="";
		
		return str;
	}
	
	

	public blastObj readConfigFromUI(Config co) throws Exception{
		
		double EvalueMax = 0;
		float Coverage = 0;
		double ScoreMin = 0;
		String group1 = "";
		String group2 = "";
		String AIEvalue = "";
		String ratioScore = "";
		String infile = "";
		String formatdb = "";
		String blastparam = "";
		String blastdb = "";
		String blastoutput = "";
		String exclusion = "";
		
		String ordHomeDir="";
		int NofTables = 1;
		
		System.out.println("Starting read..");
		AIEvalue = co.getAlienG_AIEvalue();
		System.out.println("read 1..");
		EvalueMax = co.getAlienG_EvalueMax();
		System.out.println("read 2..");
		Coverage = co.getAlienG_Coverage();
		System.out.println("read 3..");
		ScoreMin = co.getAlienG_ScoreMin();
		group1 = co.getAlienG_group1();
		group2 = co.getAlienG_group2();
		ratioScore = co.getAlienG_ratioScore();
		System.out.println("read 7..");
		infile = co.getAlienG_infile();
		blastparam = co.getAlienG_blastparam();
		exclusion = co.getAlienG_exclusion();
		blastdb = co.getAlienG_blastdb();
		/* initially, the user uploaded the genome file
		 * which is denoted by 'alieng_infile', but now
		 * the user will be asked to upload the blast
		 * output file (in xml format). 
		 */
		
		/* blastoutput=co.getAlienG_blastoutput(); */
		//points to the blast output file (in xml format).
		blastoutput=co.getAlienG_infile(); 
		
		ordHomeDir=co.getOrderHomeDir();
		return (new blastObj(EvalueMax,Coverage,ScoreMin,group1,group2,AIEvalue,ratioScore, infile, formatdb, blastparam, blastdb,blastoutput,exclusion,NofTables,ordHomeDir));
		
		
	}
	
	public blastObj readAlienGConfigFile(String fileName) throws Exception
	{
		
		//BlastXML parser
		//Evalue-thresh
		//coverage_thresh
		//scoremin_thresh
		double EvalueMax = 0;
		float Coverage = 0;
		double ScoreMin = 0;
		String group1 = "";
		String group2 = "";
		String AIEvalue = "";
		String ratioScore = "";
		String infile = "";
		String formatdb = "";
		String blastparam = "";
		String blastdb = "";
		String blastoutput = "";
		String exclusion = "";
		int NofTables = 0;
		String foobar="";
	    try {
		XMLConfiguration config = new XMLConfiguration(fileName);
		
		EvalueMax = Double.parseDouble(config.getString("parsing.Evaluemax"));
		Coverage  = Float.parseFloat(config.getString("parsing.coverage"));
		ScoreMin = Double.parseDouble(config.getString("parsing.scoremin"));
		group1 = config.getString("parsing.table1.group1");
		group2 = config.getString("parsing.table1.group2");
		AIEvalue = config.getString("parsing.table1.AIEvalue");
		ratioScore = config.getString("parsing.table1.ratioscore");
		NofTables = config.getInt("parsing.numberOfTables");
		
		infile = config.getString("blast.infile");
		formatdb = config.getString("blast.formatdb");
		blastparam = config.getString("blast.blastparam");
		blastdb = config.getString("blast.blastdb");
		blastoutput = config.getString("blast.blastoutput");
		
		exclusion = config.getString("parsing.exclusion");
		System.out.println("NofTables: " + NofTables);

		System.out.println("exclusion: " + exclusion);
		System.out.println("EvalueMax: " + EvalueMax);
		System.out.println("coverge: " + Coverage);
		System.out.println("scoreMin: " + ScoreMin);
		System.out.println("group1: " + group1);
		System.out.println("group2: " + group2);
		System.out.println("AIEvalue: " + AIEvalue);
		System.out.println("ratioScore: " + ratioScore);
		
		System.out.println("infile: " + infile);
		System.out.println("formatdb: " + formatdb);
		System.out.println("blastparam: " + blastparam);
		System.out.println("blastdb: " + blastdb);
		System.out.println("blastoutput: " + blastoutput);
		

	} catch (ConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
	return (new blastObj(EvalueMax,Coverage,ScoreMin,group1,group2,AIEvalue,ratioScore, infile, formatdb, blastparam, blastdb,blastoutput,exclusion,NofTables,foobar));
}
	
	
	

	
	
	//These next two methods are here just to test!
	public static void serialize(String outFile, Object serializableObject) throws IOException {
	    FileOutputStream fos = new FileOutputStream(outFile);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(serializableObject);
	}
		 
	public static Object deSerialize(String serilizedObject) throws FileNotFoundException, IOException, ClassNotFoundException {
	    FileInputStream fis = new FileInputStream(serilizedObject);
	    ObjectInputStream ois = new ObjectInputStream(fis);
	    return ois.readObject();
	}
	
	
}






















