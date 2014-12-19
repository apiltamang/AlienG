package al.ali.main;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import filehandler.FileHandler;
import ui.ali.Config;
import al.ali.blast.blastObj;
import al.ali.blast.localBlast;
import al.ali.mysql.MySQLAccess;
import al.ali.taxonomy.OneHitCounter;

public class Interface {
	//=============================================================================================================
	public static void main(String[] args) throws Exception {
		/*--------------------------------------------
		 * This is the entry point for the code after 
		 * all input parameters for the problem have
		 * been retrieved from the user through the GUI
		 * part of the application.
		 *-------------------------------------------*/
		
		/*
		 * masterWriter writes:
		 * 	- about a new found order, 
		 *  - whether it was successfully executed, and
		 *  - whether an exception happened on solving that order.
		 *  
		 *   The information for all orders are written to the same file: "MASTER_CONSOLE_OUTPUT.txt"
		 */
		
		/*
		 * consoleWriter writes:
		 *  - details about each order's solution process
		 *  - whether any exception occurred
		 *  - nature of the exception
		 *  
		 *  The information is written in a seperate file, identified by: "CONSOLE_OUTPUT_JOB_%d.txt"
		 *  where %d: Job#
		 */
		
		PrintWriter masterWriter=null;

		File masterConsole=new File("MASTER_CONSOLE_OUTPUT.txt");
		if(masterConsole.exists())
		{
			masterConsole.delete();
			masterConsole.createNewFile();
		}else
		{
			masterConsole.createNewFile();
		}
		
		
		//go in an infinite loop to see if any item
		//is added in the database. If yes, read it...

		while(true){ 
			PrintWriter consoleWriter=null;
			try
			{
				Thread.sleep(10000);  //sleep for ten seconds
		
				Config anOrder = MySQLAccess.readOneRequest("NewOrder", -9999);
				if(anOrder ==null)
					continue;
				/*
				 * Define a file writing stream to direct all output to a file.
				 * Will be useful for tracking jobs as they are done.
				 */
				masterWriter=new PrintWriter(new BufferedWriter(new FileWriter(masterConsole,true)));
				masterWriter.write("New Order Found: "+anOrder.getOrdNum()+"\n");
				
				
				String jobID=String.format("CONSOLE_OUTPUT_JOB_%d.txt",anOrder.getOrdNum());
				consoleWriter=new PrintWriter(jobID,"UTF-8");
				
				consoleWriter.write("One Order read...\n");
				
				consoleWriter.write("Order details: \n");
				
				/*---- Prepare to write order details to file--*/			
				blastObj configFile=new localBlast()
									.readConfigFromUI(anOrder);
				consoleWriter.write(configFile.toString()+"\n");
				/*-------------------------------------------*/
				/*
				 * Skipping the java solver, because I can't get the
				 * outputs to match with the python's results
				 
				 boolean success=new Interface().
				 					executeMainProcess(anOrder);
	     	     *Instead use the python solver
				 *-------------------------------------------*/
				boolean success=new PythonSolver(consoleWriter)
									.solveRequest(anOrder);
				
				if(success)
				{
					masterWriter.write("Order successfully completed.\n");
					//update database table to mark completion of the job.
					MySQLAccess.updateOrdersTable("processStatus","Completed",anOrder.getOrdNum());
					consoleWriter.write("Updated the MYSQL Orders Table, Order No: "+anOrder.getOrdNum()+"as: COMPLETED. \n");
					
					//email the user to download the output file.
					mail.TLSGmail.sendGmail(anOrder.getUser(), "AlienG Completed", "Please download");
					consoleWriter.write("Sent email about job status to user: "+anOrder.getUser()+"\n");
	
				}
				else
				{
					String errOut=String.format("Order FAILED to complete successfully. Please see corresponding CONSOLE_OUTPUT_JOB_%d.txt file.\n",anOrder.getOrdNum());
					masterWriter.write(errOut);
					//update database table to mark completion of the job.
					MySQLAccess.updateOrdersTable("processStatus","Error",anOrder.getOrdNum());
					consoleWriter.write("Updated the MYSQL Orders Table, Order No: "+anOrder.getOrdNum()+"as: ERROR.\n");
					
					//email the user to download the output file.
					mail.TLSGmail.sendGmail(anOrder.getUser(), "Error in job", "Please report to admin.");
					consoleWriter.write("Sent email about job status to user: "+anOrder.getUser()+"\n");
					
				}//end if (success)
			}
			catch(Exception e)
			{
				e.printStackTrace(masterWriter);
				
			}
			finally
			{
				if(!(consoleWriter==null))
					consoleWriter.close();
				
				if(!(masterWriter==null))
					masterWriter.close();
				 
			}
			 
		}//end while (//goto the next job, if there's one available)
		
		
	}//end main
	//=============================================================================================================
	public boolean executeMainProcess(Config co) throws Exception{
		try{
			/*Let the user know that this order has formally started execution.*/
			System.out.println("Main Process Started.");
			
			/* Eventually, do update the fields in the table. Not just yet
			//MySQLAccess.updateOrdersTable("processStatus","Started",co.getOrdNum()); 
			//MySQLAccess.updateOrdersTable("alienGStatus","Started",co.getOrdNum());  
			//MySQLAccess.updateOrdersTable("phyloGStatus","Waiting",co.getOrdNum());  
			//mail.TLSGmail.sendGmail(co.getUser(), "Order Started", 
			//									  "Please be on Standby");
			
			
			
			/*Comment out below to escape the actual execution.*/
			localBlast LBlast = new localBlast();
			
			
			
			
			//if first time run, set to false, else set to true
			boolean skipReadOrderFromDatabase=true;
			String configFile="AlienG_config_serialized.txt";
			blastObj AlienG_config=null;
			
			if(!skipReadOrderFromDatabase)
			{
				//read config, 
				AlienG_config = LBlast.readConfigFromUI(co);
				System.out.println(AlienG_config.toString());
				//write to local serialized file
				localBlast.serialize(configFile,AlienG_config);
			}
			else
			{
				//read from serialized file from a previous run.
				//Only use for debugging purpose. 
				System.out.println("Deserializing from configFile.");
				AlienG_config=(blastObj)localBlast.deSerialize(configFile);
				System.out.println("Done hydrating configFile.");
			}
			
			System.out.println("Starting local blast...");
			String BlastXMLoutput = LBlast.doBlast(AlienG_config);					//running the blast!		
			System.out.println("Finishing local blast..");
			System.out.println("Blast Output File: "+BlastXMLoutput);
			/*
			 * String BlastXMLoutput="C:\\AlienGFiles\\blastOutput.xml";
			 */
						
			
			ArrayList<Integer> AlienGOutput =										//get the output from the algorithm
					LBlast.ProcessBlastOutput(BlastXMLoutput, 
											  AlienG_config);
			System.out.println("Done with all steps.");
		    //MySQLAccess.updateOrdersTable("processStatus","Started",co.getOrdNum());  
			//MySQLAccess.updateOrdersTable("alienGStatus","Completed",co.getOrdNum()); 
			    
			//mail.TLSGmail.sendGmail(co.getUser(), "AlienG Completed", "Please download");

			/* =============================================================================================
			 * NOTE: 														|								||
			 * 																|								||
			 * EVERYTHING BELOW HERE WILL NO LONGER BE OPERATIONAL !!		|								||
			 * 																								\/
			 * THE JOB WILL END HERE. THE OUTPUT OF THE ALIENG ALGORITHM
			 * IS MANAGED WITHIN THE 'LBlast.ProcessBlastOutput' method.
			 * 
			 * This is because the variable 
			 * 		ArrayList<Integer>: "AlienGOutput"						|
			 * is never populated by the original author of this code, 		|
			 * and I have no plans to do that myself.						|
			 * -------------------------------------------------------------
			 * Dump AlienGOutput into the directory:
			 * [working_directory]/co.username/AlienGResult 
			*/

			
			/*
			* This will dump alienG_output into the file
			* 'AlienGOutputFile.txt' in the user's folder.
			*
			ArrayList<Integer> AlienGOutput=new ArrayList<Integer>();
			AlienGOutput.add(1);AlienGOutput.add(2);AlienGOutput.add(3);
			
			String userOrderFolder=String.format("%s\\Order-%s", co.getUser(),
																 co.getOrdNum());
			 e.g apil.tamang@gmail.com\\Order-23
			*     john.hancok@email.com\\Order-12
			*     
			*
			dumpAlienGOutput(AlienGOutput,userOrderFolder,
											   "AlienGOutputFile.txt");
			//------------------------------------------------------------
			/  -----------------------------------------------------------/
			 * Tasks:
			 * Step 1:  Write to the orders table that this order is 
			 * 			partially done. (Identified by orderNum).
			 
			  
			 * Step 2:  Prepare the file for download in 
			 * 			[project_dir]/[username]/AlienGOutputFile.txt 
			 * 			[username]= email.address
			 * ----->	This is done by working the results.jsp source code.
			 * 				ALIENG is DONE at this point.
			 *--------------------------------------------------------------/
			 		 
			 /--------------------------------------------------------------/
			 * Starting the PhyloGenie algorithm							/ 
			 com.ali.Interface.main.phylogenie(AlienGOutput,userOrderFolder);
			 /
			 * where
			 * AlienGOutput: read result of AlienG algorithm.
			 * co.getUser(): provide the directory name (user.email@domain.com)
			 *				 to store blast results.
			 * ``````````````````````````````````````````````````````````````
			 * After PhyloG algorithm completes, do following
			 * Step 1	   : Implement an archiver to archive blastresults.zip
			 * 				 [DONE!]										/
			 String destZipFile=userOrderFolder+"\\blastresults.zip";
			 al.ali.blast.Zipper.zip(userOrderFolder, destZipFile);
			 /
			 * Step 2	   : Make the archive file downloadable from 
			 * 				 results.jsp 						[DONE BELOW]
			 MySQLAccess.updateOrdersTable("processStatus","Completed",co.getOrdNum()); 
			 MySQLAccess.updateOrdersTable("alienGStatus","Completed",co.getOrdNum());  
			 MySQLAccess.updateOrdersTable("phyloGStatus","Completed",co.getOrdNum()); 
			 mail.TLSGmail.sendGmail(co.getUser(), "PhyloG Completed", "Please download");
			 
			 *  Step 3	   : Do something about the "trees".
			 * ``````````````````````````````````````````````````````````````			     					/\
			 * For now, I will skip the entire "trees" generation, which is										||
			 * accomplished by wrapping tr.main(f) in an unreachable code 										||
			 * block. (see com.ali.Interface/main)																||
			 * -------------------------------------------------------------								 	||
			 * =================================================================================================
			 * */

			 
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("Execution of main process failed.");
		}
	
	} //end method alieng2
	//=============================================================================================================
	public void dumpAlienGOutput(ArrayList<Integer> intList, String dirName, String fileName) throws IOException{
		/* -----------------------------------------------------
		 * Dump AlienGOutput into the directory:
		 * [working_directory]/co.username/AlienGResult 	
		 * The ArrayList is appended to the file, so that 
		 * if the file already exists, write operation succeeds.	
		/* ----------------------------------------------------*/
		try{
			//return File object given the directory and file names;
			File file=FileHandler.returnFileHandle(dirName, fileName);
			//get a fileWriter object
			FileWriter fw=new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw=new BufferedWriter(fw);
			for(Integer i: intList){
				//write to file
				bw.write(i.toString());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}catch(Exception e){
			System.out.println("Exception error caught");
			e.printStackTrace();
		}finally{
			;
		}
	}//end dumpAlienGOutput
	

}
