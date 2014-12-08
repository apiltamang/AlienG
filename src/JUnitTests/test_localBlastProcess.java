package JUnitTests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.blast.parser.main.XMLParser;
import org.blast.parser.objects.BlastOutput;
import org.blast.parser.objects.Hit;
import org.blast.parser.objects.Hsp;
import org.blast.parser.objects.Iteration;
import org.junit.After;
import org.junit.Test;

import ui.ali.Config;

import com.ali.Interface.JavaRunCommand;

import java.sql.Connection;

import javax.xml.bind.JAXBException;

import al.ali.blast.*;
import al.ali.main.Interface;
import al.ali.mysql.MySQLAccess;
import al.ali.taxonomy.OneHit;
import al.ali.taxonomy.OneHitCounter;

public class test_localBlastProcess {

	@After
	public void tearDown() throws Exception {
	}

	
	public void test() {
		blastObj dummy = new blastObj();
		
		localBlast lBlast=new localBlast();
		
		String testFooBarRun=lBlast.doBlast(dummy);
		
		System.out.println("Testing localBlast.doBlast(blastObj)...");
		assertTrue(true);
	}

	//@Test
	public void test2()
	{
		String sys_cal = "C:\\Program Files\\NCBI\\blast-2.2.28+\\bin\\blastdbcmd -entry 515462380 -db C:\\db\\nr -outfmt \"%S,%T\" -out C:\\db\\blastcmdOutput.txt";
		System.out.println("sys_cal= " + sys_cal );
		//call sys_call
		JavaRunCommand a = new JavaRunCommand();
		a.run(sys_cal);
	}
	
	//@Test
	public void test3()
	{
		Config dummy=new Config();
		Interface aTest=new Interface();
		try {
			boolean success=aTest.executeMainProcess(dummy);
			assertTrue(success);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	//@Test
	public void test4()
	{
		String sys_cal= "C:\\Program Files\\NCBI\\blast-2.2.28+\\bin\\blastp -query C:\\AlienGTestInputFiles\\only_two_sequence.fasta -out C:\\AlienGTestInputFiles\\output.xml -db C:\\db\\nr -outfmt 5 -evalue 1e-5 -num_threads 8";
		JavaRunCommand a = new JavaRunCommand();
		a.run(sys_cal);
	}
	
	//@Test
	public void test_parseXMLAndSaveToSerializedFile()
	{
		XMLParser parser=new XMLParser();
		String serializedBlastFile="C:\\AlienGSerializedFiles\\Blast_Output.txt";
		try 
		{
			BlastOutput blastOutput=parser.parseXML("C:\\AlienGSerializedFiles\\blastOutput.xml");
			localBlast.serialize(serializedBlastFile,blastOutput);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	// In the following test, I will run the entire processBlast method.
	@Test
	public void test_processBlastOutput()
	{
		try{
			String configFile="C:\\AlienGTestInputFiles\\AlienG_config_serialized.txt";
			String blastFile ="C:\\AlienGTestInputFiles\\output.xml";
			
			
		   //read from serialized file
			blastObj AlienG_config=null;
			AlienG_config=(blastObj)localBlast.deSerialize(configFile);
			
			//parse xml file to get object
			XMLParser parser=new XMLParser();
			BlastOutput blastOutput=null;
			blastOutput=parser.parseXML(blastFile);
			
			//get connection to mysql database
			MySQLAccess sql=new MySQLAccess();
			Connection connect=sql.newConnection();
			//------------------------------------------------------------------------
			//--Straight from the processBlastOutput method.
			boolean skip_flag;
			int hsp_count;
			int hsp_len;
			float coverage;
			String hit_lineage = "";
			
			ArrayList<String> gi_list = new ArrayList<String>();
						
	 		HashMap<String, OneHit> Hit_obj_Map = new HashMap<String, OneHit>();

			HashMap<String, OneHitCounter> Hit_counter_obj_list = new HashMap<String, OneHitCounter>();
			ArrayList<String> Hit_tax_id_list = new ArrayList<String>();//Create tax_id list for every hit of different seq_ID

			Timer timerSQL=new Timer();
			Timer timerBlastDB=new Timer();
			//-----------------------------------------------------------------
			//---Start iterating through each query sequence in the
			//---genome file
			for (Iteration iteration : blastOutput.getBlastOutput_iterations()){

			

				String query_id = iteration.getIteration_query_def();
				


					
				int query_len = iteration.getIteration_query_len();
				int hit_no = 0;
				int query_hit_count = 0;
				if(iteration.getIteration_hits() != null)	
				for (Hit alignment : iteration.getIteration_hits()){
					hit_no++;
					skip_flag = false;
					hsp_count = 0;
					for(Hsp hsp : alignment.getHit_hsps()){
						if(hsp_count == 0){//Only get the first hsp
							hsp_len = hsp.getHsp_align_len();
							coverage = ((float)hsp_len/(float)query_len);
							
							if((Double.compare(hsp.getHsp_evalue(), AlienG_config.getEvalueMax()) < 0 ) && ( Double.compare(hsp.getHsp_bit_score(), AlienG_config.getScoreMin()) > 0 ) && (Float.compare(coverage, AlienG_config.getCoverage()) > 0))
							{
								
								String title = alignment.getHit_id() + " "+ alignment.getHit_def();
								 //never had a case to test this for 
								if(AlienG_config.getExclusion() != null){ 
									for(String excluded: AlienG_config.getExclusion()){
										 if(alignment.getHit_def().toUpperCase().contains(excluded.toUpperCase())){
											 skip_flag = true;
											 break;
										 }
									 }
								}
								if (skip_flag == true){
									System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + " filtered");
									continue;
								}
								
								//#Rule out:Hit without species name,Hit_def:No definition line found
								// i don't really see any case in which this condition wouldn't pass
								if(title.contains("No definition line found") == false){  
									System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + "not filtered");
									System.out.println("title=  " + title);
									
									String t[] = title.split("\\]");
									int h = t.length - 2;
									if(h < 0) h += t.length;
									
									String t1[] = t[h].split("\\[");
									String id[] = alignment.getHit_id().split("\\|");
									String hit_id = id[1];//get gi
									
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
	                                    
	                                    //--------------------------------------------------
	                                    timerSQL.startTimer();									//
	                                    String taxID = sql.mapNameToId(connect,hit_name);   //
	                                    timerSQL.stopTimer("sql.mapNameToId");                 //
	                                    //--------------------------------------------------
	                                    //taxID could be null. need to be checked
									    //System.out.println("sign [] happend !!!!!!");


	                                    System.out.println("taxID= " + taxID);
										if(taxID != null){//if species do not have tax_id,no record
											if_species_name_match = true;
		                                     String hit_tax_id = taxID;
		                                     Hit_tax_id_list.add(taxID);
		                                     double hit_score = hsp.getHsp_bit_score();
		                                     double hit_evalue = hsp.getHsp_evalue();
		                                     int hit_identity = hsp.getHsp_identity();
		                                     int hit_align_len = hsp.getHsp_align_len();
		                                     // not sure if these are actually working or not!
		                                     String Key = query_id + "|" + Integer.toString(hit_no);
		                                     Hit_obj_Map.put(Key, new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len));
		                                     //Hit_obj_list.add(new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len));
		                                     query_hit_count++;
										}
										
										//just for test
										//if(taxID == null)
										//	 System.out.println("ali= ");
	                                     
									}
									if(if_exist_species_name == false || if_species_name_match == false){
										// this is test: String hit_def = alignment.getHit_id();
										String hit_def = alignment.getHit_def();
										System.out.println("hit_def= " + alignment.getHit_def() );
										if(hit_def.contains("gi|")){//find gi in hit_def
											String pos[] = hit_def.split("\\|");
											
											if(pos.length >= 2){ // exist gi
												hit_id = pos[1];//get gi
												if(!gi_list.contains(hit_id)){
													//sys_call
													timerBlastDB.startTimer();
													String sys_cal = "C:\\Program Files\\NCBI\\blast-2.2.28+\\bin\\blastdbcmd -entry " + hit_id + " -db C:\\db\\nr -outfmt \"%S,%T\" -out C:\\db\\tempFile.txt";
													timerBlastDB.stopTimer("blastdb command");
													System.out.println("sys_cal= " + sys_cal );
													//call sys_call
													JavaRunCommand a = new JavaRunCommand();
													a.run(sys_cal);
													String fileLocation = "C:\\db\\tempFile.txt";
													
													BufferedReader temp_handle = null;										 
													temp_handle = new BufferedReader(new FileReader(fileLocation));
													String temp_line = temp_handle.readLine();
													//split ..
													//we need strips here
													
													String gi_pos[] = temp_line.split("\\,");
													
													if(gi_pos.length == 2){
														String hit_name = gi_pos[0];
														Integer taxID = Integer.parseInt(gi_pos[1]);
														System.out.println("gi: " + hit_id + " ,hit_name: "+ hit_name + " ,tax_id: " + taxID);
														int hit_len = alignment.getHit_len();
														String hit_annotation = "No species name in BLAST output, so get tax_id from gi";
														String hit_tax_id = taxID.toString();
														Hit_tax_id_list.add(hit_tax_id);
														double hit_score = hsp.getHsp_bit_score();
														double hit_evalue = hsp.getHsp_evalue();
														int hit_identity = hsp.getHsp_identity();
														int hit_align_len = hsp.getHsp_align_len();
														//OneHit a = new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len);
														//Hit_obj_list.add(new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len));
														String Key = query_id + "|" + Integer.toString(hit_no);
					                                    Hit_obj_Map.put(Key, new OneHit(query_id, hit_no, hit_id, hit_len, coverage, hit_name, hit_tax_id, hit_score, hit_evalue, hit_lineage, hit_annotation,hit_identity,hit_align_len));
														query_hit_count++;	
													}
													else{//using gi could not find tax_id
														if(!gi_list.contains(hit_id)){
															gi_list.add(hit_id);	
														}
														System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + ", we could not find species name and gi,so ignore this record!");
													}
													temp_handle.close();										
												}
											}
											
										}
										else{//could not find gi
											System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + " we could not find species name and gi,so ignore this record!");
										}
									}
									
								}
							}
							else{
								System.out.println("For sequence: " + query_id + " Hit No.:  "+  hit_no + " cut off by Evalue_threshold,ScoreMin,coverage");
							}
							hsp_count++;
								
						}
						
						
					}
				}
				
				//Hit_counter_obj_list.add(qnew OneHitCounter(query_id, query_hit_count));
				Hit_counter_obj_list.put(query_id, new OneHitCounter(query_id, query_hit_count));
			}
		
			timerSQL.printStatistics("sql.mapNameToID");
			timerBlastDB.printStatistics("blastDB command");
			
			System.out.println("Hits Done!");
			System.out.println("Start creating lineage information.....");
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

}
