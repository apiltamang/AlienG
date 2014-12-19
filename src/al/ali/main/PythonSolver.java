package al.ali.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;






import al.ali.mysql.MySQLAccess;



import ui.ali.Config;
import filehandler.FileHandler;

public class PythonSolver {

	File progressFile;
	PrintWriter consoleWriter;
	
	public PythonSolver(PrintWriter consoleWriter)
	{
		this.consoleWriter=consoleWriter;
	}
	public boolean solveRequest(Config config) 
	{
		boolean success;
		try
		{
			//update the database table to mark that current job has begun.
			//MySQLAccess.updateOrdersTable("processStatus","Started",config.getOrdNum());
			
			//create a config file that will be used to drive the python solver
			PythonSolverHelper.createConfigFile(config);
			
			String root="python C:\\AlienG\\interface.py -c "+
							config.getOrderHomeDir()+"config.conf";
			
			
			String progressReport=config.getOrderHomeDir()+"progressFile.txt";
			consoleWriter.write("Details of the process are directed to file: \n");
			consoleWriter.write("    "+progressReport+"\n");
			
			
			/*---The progress file will document the output from the python solver
			 *  that would/should have normally been written to the console 
			 */
			progressFile=new File(progressReport);
			if(progressFile.exists())
			{
				progressFile.delete();
				progressFile.createNewFile();
			}else
			{
				progressFile.createNewFile();
			}
			consoleWriter.write("progressFile is created.\n");
			/*
			 * Copy all the taxonomy mapping files from the backup to a specific directory
			 * will be used to fulfill the analysis for this order.
			 */

			copyFilesFromTaxMappingBackupToWorkDir(config);
				
			/*
			 * parse blast output file (blast xml file is read from config.conf file)
			 * + perform the alieng analysis as well!
			 */
			String parseXML=root+" -x ";
			consoleWriter.write("Initializing Python Command : \n    "+parseXML+"\n");
			Thread.sleep(10000);
			run(parseXML);
			consoleWriter.write("Job done.\n");
			
			//Now move the files: DEFAULT_OUTPUT_FILE.txt, and only_group1_file.txt to order home dir
			String srcDir="C:\\AlienG";
			String dstDir=config.getOrderHomeDir();
			if(!FileHandler.moveFile(srcDir, dstDir, "DEFAULT_OUTPUT_FILE.txt"))
				consoleWriter.write("FAILED to move DEFAULT_OUTPUT_FILE.txt from \n "+srcDir+" ---to--> "+dstDir+"\n");
			
			consoleWriter.write("Moved the output file: DEFAULT_OUTPUT_FILE.txt from \n "+srcDir+" ---to--> "+dstDir+"\n");
			if(!FileHandler.moveFile(srcDir, dstDir, "ONLY_GROUP1_OUTPUT.txt"))
				consoleWriter.write("FAILED to move ONLY_GROUP1_OUTPUT.txt from \n "+srcDir+" ---to--> "+dstDir+"\n");
			
			consoleWriter.write("Moved the output file: ONLY_GROUP1_OUTPUT.txt from \n "+srcDir+" ---to--> "+dstDir+"\n");
			
			success=true;
			
		}
		catch(Exception e)
		{
			consoleWriter.write("Exception was raised while solving this request.\n");
			consoleWriter.write("Exception Message: \n"+e.getMessage()+"\n");
			consoleWriter.write("Exception StackTrace: \n");
			e.printStackTrace(consoleWriter);
			
			success= false;
		}
		finally
		{
			consoleWriter.write("This order has been processed. Moving on to a new order...\n");
			//At this point, remove the intermediate files (and input blast files) created in this job
			try
			{
				deleteIntermediateFilesFolders(config);
			}
			catch(Exception e)
			{
				e.printStackTrace(consoleWriter);
			}
			
		}
		return success;
	}//
	
	public void deleteIntermediateFilesFolders(Config config) throws Exception
	{
		try
		{
			File blastXML=new File(config.getBlastOutputXMLFile());
			
			if(blastXML.exists())
			{
				//this should always be the case!
				consoleWriter.write("BLAST XML file deleted: \n    "+blastXML.toString()+"\n");
				blastXML.delete();
				
			}
			else
			{
				consoleWriter.write("BLAST XML file not found for deletion: \n    "+blastXML.toString()+"\n");
			}
			
			//delete all files in the working tax mappings dir
			File taxMapDir=new File(config.taxMappingDir);
			
			consoleWriter.write("Deleting files in the working tax mapping dir: \n");
			
			File[] files=taxMapDir.listFiles();
			for(File aF: files)
			{
				consoleWriter.write("Deleting file: "+aF.getName()+"\n");
				aF.delete();
			}
			
			//the tax map dir should now be empty, and amenable to being deleted
			consoleWriter.write("Deleting the working tax mapping dir: "+taxMapDir.getName()+"\n");
			taxMapDir.delete();
			
			consoleWriter.write("Tax mapping dir + files, and BLAST XML file for this job deleted...\n");
		}
		catch(Exception e)
		{
			throw new Exception("Exception occurred while deleting files and folders for the job.\n Exception Message: \n"+e.getMessage()+"\n");
		}
	

	}
	public void writeToProgressFile(String str)throws IOException, Exception
	{
		try
		{
			PrintWriter writer=new PrintWriter(new BufferedWriter(new FileWriter(progressFile,true)));
			writer.println(str);
			writer.close();
		}
		catch(IOException ioe)
		{
			throw new IOException("IOException occured while trying to write to progress file: \n    "+progressFile.toString()+"\n Exception Message: \n"+ioe.getMessage()+"\n");
		}
		catch(Exception e)
		{
			throw new Exception ("Exception occured while trying to write to progress file: \n    "+progressFile.toString()+"\n Exception Message: \n"+e.getMessage()+"\n");
		}
		
	}

	public void copyFilesFromTaxMappingBackupToWorkDir(Config config) throws Exception
	{
		if(FileHandler.readyDirectory(config.taxMappingDir)==0)
		{
			consoleWriter.write("Tax Mapping Directory Already Exists for this order..Deleting all files. \n");
			if(!FileHandler.deleteAllFilesInDir(config.taxMappingDir))
				throw new Exception("Could not successfully create a tax mapping working dir: "+config.taxMappingDir+"\n");
			
			consoleWriter.write("Pre-existing Tax Mapping Directory deleted along with its contents for this job.\n");
		}
		if(!FileHandler.copyAllFilesInDir("C:\\AlienG\\taxonomy_mappings_backup", config.taxMappingDir))
			throw new Exception("Could not copy tax mapping files into a working dir for Order: \n    "+config.toString()+"\n");
		consoleWriter.write("Tax mapping files copied from the backup dir. to the working dir. for this job\n");
		
	}
    private void run(String CMD)throws Exception
    {
        try 
        {
            // Run "netsh" Windows command
            Process process = Runtime.getRuntime().exec(CMD);

            // Get input streams
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Read command standard output
            String s;
            //System.out.println("Standard output: ");
            while ((s = stdInput.readLine()) != null) {
                writeToProgressFile(s);
            }

            // Read command errors
            //System.out.println("Standard error: ");
            while ((s = stdError.readLine()) != null) {
                writeToProgressFile(s);
            }
        } 
        catch (Exception e) 
        {
        	throw new Exception("Exception occurred while calling the \"run\" method that invokes the python solver.\n Exception Message: \n"+e.getMessage()+"\n");
        }
    }
}
