package al.ali.main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import ui.ali.Config;

public class PythonSolverHelper {
	public static void createConfigFile(Config config) throws Exception
	{
		try
		{
			String inputConfigFile=config.getOrderHomeDir()+"config.conf";
			config.setBlastOutputXMLFile(config.getAlienG_infile());
			config.taxMappingDir="C:\\AlienG\\TaxMappingOrder_"+config.getOrdNum();
			PrintWriter writer=new PrintWriter(inputConfigFile,"UTF-8");
			
			//Now create the config file
			writer.println("[blast_start]");
	
			writer.println(" ");
			
			writer.println("[infile]=foobar.query");
			
			writer.println("");
			
			writer.println("[formatdb]=irrelevant");
			
			writer.println("");
			
			writer.println("[blast]=foobarAllTheWay");
			
			writer.println("");
			
			writer.println("[blastparams]=irrelevant");
			
			writer.println("");
			
			writer.println("[blastdb]=C:\\AlienG\\blast_database\\nr");
			
			writer.println("");
			
			writer.println("[blastoutput]="+config.getBlastOutputXMLFile());
			
			writer.println("");
			
			writer.println("[blast_end]");
			
			writer.println("");
			
			writer.println("[taxdir]=C:\\AlienG\\taxonomy_database");
			
			writer.println("");
			
			/*
			 *
			 * I will not attempt to parse the taxonomy_
			 * database for each job. In contrast, this
			 * parsing has already been done, and is stored
			 * in dir: "C:\\AlienG\\taxonomy_mappings_backup".
			 * For each job, these mappings file will be copied
			 * into a working folder, and used for the analysis.
			 * The working folder is:
			 * C:\\AlienG\\TaxMappingOrder_X, where X = Order #
			 * 
			 */
			writer.println("[taxmappingdir]="+config.taxMappingDir);
			
			writer.println("");
			
			writer.println("[exclusion]="+config.getAlienG_exclusion());
			
			writer.println("");
			
			writer.println("[Evaluemax]="+config.getAlienG_EvalueMax());
			
			writer.println("");
			
			writer.println("[coverage]="+config.getAlienG_Coverage());
			
			writer.println("");
			
			writer.println("[scoremin]="+config.getAlienG_ScoreMin());
			
			writer.println("");
			
			writer.println("[table_start]=1");
			
			writer.println("");
			
			writer.println("[table1]");
			
			writer.println("");
			
			writer.println("[group1]="+config.getAlienG_group1());
			
			writer.println("");
			
			writer.println("[group2]="+config.getAlienG_group2());
			
			writer.println("");
			
			writer.println("[AIEvalue]="+config.getAlienG_AIEvalue());
			
			writer.println("");
			
			writer.println("[ratioscore]="+config.getAlienG_ratioScore());
			
			writer.println("");
			
			writer.println("[/table1]");
			
			writer.println("");
			
			writer.println("[table_end]");
			writer.close();
		}
		catch(Exception e)
		{
			throw new Exception(" There was an error creating config.conf file for the job. Exception Message: \n"+e.getMessage()+"\n");
		}

	}
}
