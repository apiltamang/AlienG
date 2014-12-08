package al.ali.blast;

import java.io.Serializable;
import java.util.ArrayList;

public class Zip implements Serializable {
	private ArrayList<String> child_to_parent_name=new ArrayList<String>();
	private ArrayList<String> child_to_parent_taxID=new ArrayList<String>();

	public Zip(ArrayList<String> child_to_parent_name,
				ArrayList<String> child_to_parent_taxID) {
		this.child_to_parent_name = child_to_parent_name;
		this.child_to_parent_taxID = child_to_parent_taxID;
	}
	
	public ArrayList<String> getChild_to_parent_name() {
		return child_to_parent_name;
	}

	public ArrayList<String> getChild_to_parent_taxID() {
		return child_to_parent_taxID;
	}
	
	@Override
	public String toString()
	{
		String zipStr="";
		String indent="    ";
		zipStr ="child_to_parent_name\n";
		for (String str:child_to_parent_name)
		{
			zipStr = zipStr+indent+"-->"+str+"\n";
		}
		
		zipStr = zipStr+"child_to_parent_taxID\n";
		for(String str:child_to_parent_taxID)
		{
			zipStr=zipStr+indent+"-->"+str+"\n";
		}
		
		
		return zipStr;
	}
	
}
