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
	public void solveRequest(Config config) throws Exception
	{
		//update the database table to mark that current job has begun.
		//MySQLAccess.updateOrdersTable("processStatus","Started",config.getOrdNum());
		
		//create a config file that will be used to drive the python solver
		PythonSolverHelper.createConfigFile(config);
		String root="python C:\\AlienG\\interface.py -c "+
						config.getOrderHomeDir()+"config.conf";
		
		String progressReport=config.getOrderHomeDir()+"progressFile.txt";
		progressFile=new File(progressReport);
		
		if(progressFile.exists())
		{
			progressFile.delete();
			progressFile.createNewFile();
		}else
		{
			progressFile.createNewFile();
		}
		
		/*
		 * Copy all the taxonomy mapping files from the backup to a specific directory
		 * will be used to fulfill the analysis for this order.
		 */
		//create dest dir
		copyFilesFromTaxMappingBackupToWorkDir(config);
			
		/*
		 * parse blast output file (blast xml file is read from config.conf file)
		 * + perform the alieng analysis as well!
		 */
		String parseXML=root+" -x ";
		writeToProgressFile("Initializing Main Process : "+parseXML);
		Thread.sleep(5000);
		//run(parseXML);
		writeToProgressFile("Job done.");
		
		//Now move the files: DEFAULT_OUTPUT_FILE.txt, and only_group1_file.txt to order home dir
		String srcDir="C:\\AlienG";
		String dstDir=config.getOrderHomeDir();
		if(!FileHandler.moveFile(srcDir, dstDir, "DEFAULT_OUTPUT_FILE.txt"))
			System.out.println("Failed to move default output file to order home dir.");
		
		if(!FileHandler.moveFile(srcDir, dstDir, "only_group1_file.txt"))
			System.out.println("Failed to move group1 file to order home dir.");
		
		//update database table to mark completion of the job.
		//MySQLAccess.updateOrdersTable("processStatus","Completed",config.getOrdNum());
		
		//email the user to download the output file.
		mail.TLSGmail.sendGmail(config.getUser(), "AlienG Completed", "Please download");
		
		//At this point, remove the intermediate files (and input blast files) created in this job
		deleteIntermediateFilesFolders(config);
	}//
	
	public boolean deleteIntermediateFilesFolders(Config config)
	{
		File blastXML=new File(config.getBlastOutputXMLFile());
		
		if(blastXML.exists())
		{
			//this should always be the case!
			System.out.println("File delete: "+blastXML.toString());
			blastXML.delete();
		}
		else
		{
			System.out.println("File not found: "+blastXML.toString());
		}
		
		//delete all files in the working tax mappings dir
		File taxMapDir=new File(config.taxMappingDir);
		
		File[] files=taxMapDir.listFiles();
		for(File aF: files)
		{
			aF.delete();
		}
		
		//the tax map dir should now be empty, and amenable to being deleted
		taxMapDir.delete();
		
		return true;
	}
	public void writeToProgressFile(String str)throws IOException
	{
		PrintWriter writer=new PrintWriter(new BufferedWriter(new FileWriter(progressFile,true)));
		writer.println(str);
		writer.close();
		
	}

	public void copyFilesFromTaxMappingBackupToWorkDir(Config config) throws Exception
	{
		if(FileHandler.readyDirectory(config.taxMappingDir)==0)
		{
			System.out.println("Tax Mapping Directory Already Exists for this order..Deleting all files. ");
			if(!FileHandler.deleteAllFilesInDir(config.taxMappingDir))
				throw new Exception("Could not successfully create a tax mapping working dir: "+config.taxMappingDir);
		}
		if(!FileHandler.copyAllFilesInDir("C:\\AlienG\\taxonomy_mappings_backup", config.taxMappingDir))
			throw new Exception("Could not copy tax mapping files into a working dir for Order: "+config.toString());
		
		
	}
    private void run(String CMD)
    {
        try {
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
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
