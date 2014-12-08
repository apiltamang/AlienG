package al.ali.blast;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import al.ali.mysql.MySQLAccess;

public class blastObj implements Serializable{
	private double EvalueMax = 0;


	private float Coverage = 0;
	private double ScoreMin = 0;
	private String group1;
	//private String[] list_group1_taxID;
	//private String[] list_group2_taxID;
	private ArrayList<String> list_group1_taxID = new ArrayList<String>();
	private ArrayList<String> list_group2_taxID = new ArrayList<String>();
	private String group2;
	private String AIEvalue;
	private String ratioScore;
	private String infile;
	
	private String[] exclusion;
	private String[] exclusionTaxId;
	private String formatdb;
	private String blastparam;
	private String blastdb;
	private String blastoutput;
	
	private int numberOfTables;
	private String orderHomeDir;
	
	public String getOrderHomeDir()
	{
		return this.orderHomeDir;
	}
	public blastObj()
	{
		
	}
	
	@Override
	public String toString()
	{
		String str="";
		String indent="    ";
		str+="\n";
		str+="AlienG Configuration Parameters"+"\n";
		str +="Coverage: "+Coverage+"\n";
		str +="ScoreMin: "+ScoreMin+"\n";
		str+="\n";
		str +="Group1 List Names: "+group1+"\n";
		str +="Group1 List TaxID: "+"\n";
		for(String temp: list_group1_taxID)
		{
			str +=indent+temp+"\n";
			
		}
		str+="\n";
		str +="Group2 List Names: "+group2+"\n";
		str +="Group2 List TaxID: "+"\n";
		for(String temp:list_group2_taxID)
		{
			str +=indent+temp+"\n";
		}
		str+="\n";
		str +="AIEValue:   "+AIEvalue+"\n";
		str +="RatioScore: "+ratioScore+"\n";
		
		str+="\n";
		
		str+="Exclusion List Names: "+"\n";
		for(int ii=0;ii<exclusion.length;ii++)
		{
			str +=exclusion[ii]+"\n";
		}
		str+="\n";
		str+="Exclusion List TaxID: "+"\n";
		for(int ij=0;ij<exclusionTaxId.length;ij++)
		{
			str +=exclusionTaxId[ij]+"\n";
		}
		str+="\n";
		str+="Number of Tables: "+numberOfTables+"\n";
		str+="Order Home Dir:   "+orderHomeDir+"\n";
		
		return str;
	}
	
	public blastObj(double evalueMax, float coverage, double scoreMin,
			String group1, String group2, String aIEvalue, String ratioScore, String infile, String formatdb, String blastparam, String blastdb, 
			String blastoutput, String exclusion,int NofTables,String orderHomeDir) throws Exception {
		
		EvalueMax = evalueMax;
		Coverage = coverage;
		ScoreMin = scoreMin;
		this.group1 = group1;
		this.group2 = group2;
		AIEvalue = aIEvalue;
		this.ratioScore = ratioScore;
		this.infile = infile;
		this.formatdb = formatdb;
		this.blastparam = blastparam;
		this.blastdb = blastdb;
		this.blastoutput = blastoutput;
		this.numberOfTables = NofTables;
		this.orderHomeDir = orderHomeDir;
		
		if(exclusion != null)
			this.exclusion = exclusion.split(";");
		else exclusion = null;
		
		MySQLAccess sql = new MySQLAccess();
		Connection connect=null;
		connect = sql.newConnection();
		
		
		if(exclusion != null){
			this.exclusionTaxId = new String[this.exclusion.length];
			for(int i=0; i<this.exclusion.length; i++){
				this.exclusionTaxId[i] = "No tax_id";
				String taxID = sql.mapNameToId(connect,this.exclusion[i]);
				if(taxID != null)
					exclusionTaxId[i] = taxID;
				
			}
		}
		else exclusionTaxId = null;
		
		
		
		for(int k=0; k<this.getGroup1().split(" 'OR' ").length; k++){
			String taxID = sql.mapNameToId(connect,this.getGroup1().split(" 'OR' ")[k]);
			
			if(taxID != null)
				list_group1_taxID.add(taxID);
			else list_group1_taxID.add("No tax_id");

		}
		
		for(int k=0; k<this.getGroup2().split(" 'OR' ").length; k++){
			String taxID = sql.mapNameToId(connect,this.getGroup2().split(" 'OR' ")[k]);
			
			if(taxID != null)
				list_group2_taxID.add(taxID);
			else list_group2_taxID.add("No tax_id");		}
		
		
		sql.close(connect);
	}
	

	
	
	public void setExclusionTaxId(String[] exclusionTaxId) {
		this.exclusionTaxId = exclusionTaxId;
	}




	public String[] getExclusionTaxId() {
		return exclusionTaxId;
	}




	public ArrayList<String> getList_group1_taxID() {
		return list_group1_taxID;
	}




	public void setList_group1_taxID(ArrayList<String> list_group1_taxID) {
		this.list_group1_taxID = list_group1_taxID;
	}




	public ArrayList<String> getList_group2_taxID() {
		return list_group2_taxID;
	}




	public void setList_group2_taxID(ArrayList<String> list_group2_taxID) {
		this.list_group2_taxID = list_group2_taxID;
	}




	public double getEvalueMax() {
		return EvalueMax;
	}

	public String[] getExclusion() {
		return exclusion;
	}

	public float getCoverage() {
		return Coverage;
	}

	public double getScoreMin() {
		return ScoreMin;
	}
	
	public int getNumberOfTables() {
		return numberOfTables;
	}


	public String getInfile() {
		return infile;
	}



	public String getFormatdb() {
		return formatdb;
	}



	public String getBlastparam() {
		return blastparam;
	}



	public String getBlastdb() {
		return blastdb;
	}



	public String getBlastoutput() {
		return blastoutput;
	}

	public String getGroup1() {
		return group1;
	}

	public String getGroup2() {
		return group2;
	}

	public String getAIEvalue() {
		return AIEvalue;
	}

	public String getRatioScore() {
		return ratioScore;
	}




	public void setGroup1(String group1) {
		this.group1 = group1;
	}




	public void setGroup2(String group2) {
		this.group2 = group2;
	}
	
	
	
	
}
