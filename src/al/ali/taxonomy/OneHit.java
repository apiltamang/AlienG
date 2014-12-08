package al.ali.taxonomy;

import java.io.Serializable;

/**
 * @author AHMADVANDA11
 * @date Jan 26, 2013
 */
public class OneHit implements Serializable{
	private static final long serialVersionUID=1294537096026224490L;
	private String queryId;
	private int hitNo = 1;
	private String hitId;
	private int hitLen = 0;
	private float coverage = 0;
	private String name = "";
	private String nameWithWhiteSpace = "";
	private String taxId;
	private int identities = 0;
	private int alignLen = 0;
	private double score = 0;
	private double evalue;
	private String lineage = "";
	private String annotation = "";
	
public OneHit(String query_id,int hit_no, String hit_id, int hit_len, float cov ,String hit_name, String hit_tax_id, double s, double ev, String lin, String hit_annotation, int iden, int align_len  ){
	this.queryId = query_id;
	this.hitNo = hit_no;
	this.hitId = hit_id;
	this.hitLen = hit_len;
	this.coverage = cov;
	this.name = hit_name;
	this.nameWithWhiteSpace = hit_name;
	this.taxId = hit_tax_id;
	this.identities = iden;
	this.alignLen = align_len;
	this.score = s;
	this.evalue = ev;
	this.lineage = lin;
	this.annotation = hit_annotation;
}

@Override
public String toString()
{
	String indentBy="        ";
	String objToString="OneHit"+"\n";
	
	objToString +=indentBy+"queryID: 				"+this.queryId+"\n";
	objToString +=indentBy+"hitNo:	 				"+this.hitNo+"\n";
	objToString +=indentBy+"hitId:   				"+this.hitId+"\n";
	objToString +=indentBy+"hitLen:  				"+this.hitLen+"\n";
	objToString +=indentBy+"coverage:				"+this.coverage+"\n";
	objToString +=indentBy+"name:    				"+this.name+"\n";
	objToString +=indentBy+"Name With white space: 	"+"\n";
	objToString +=indentBy+"taxId:   				"+this.taxId+"\n";
	objToString +=indentBy+"identities: 			"+this.identities+"\n";
	objToString +=indentBy+"alignLen:				"+this.alignLen+"\n";
	objToString +=indentBy+"score:   				"+this.score+"\n";
	objToString +=indentBy+"evalue:  				"+this.evalue+"\n";
	objToString +=indentBy+"lineage: 				"+this.lineage+"\n";
	objToString +=indentBy+"annotation: 			"+this.annotation+"\n";
	
	return objToString;
}
public String getQueryId() {
	return queryId;
}

public int getHitNo() {
	return hitNo;
}

public String getHitId() {
	return hitId;
}

public int getHitLen() {
	return hitLen;
}

public float getCoverage() {
	return coverage;
}

public String getName() {
	return name;
}

public String getNameWithWhiteSpace() {
	return nameWithWhiteSpace;
}

public String getTaxId() {
	return taxId;
}

public int getIdentities() {
	return identities;
}

public int getAlignLen() {
	return alignLen;
}

public double getScore() {
	return score;
}

public double getEvalue() {
	return evalue;
}

public String getLineage() {
	return lineage;
}

public String getAnnotation() {
	return annotation;
}



}
