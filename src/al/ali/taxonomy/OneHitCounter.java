package al.ali.taxonomy;

import java.io.Serializable;

/**
 * @author AHMADVANDA11
 * @date Feb 2, 2013
 */
public class OneHitCounter implements Serializable {

	private static final long serialVersionUID=-5148292492767392827L;
	String query_id;
	int hit_counter = 1;
	
	public OneHitCounter(String qi, int ht){
		this.query_id = qi;
		this.hit_counter = ht;
		
	}
	
	@Override
	public String toString()
	{
		String indent="        ";
		String objString="OneHitCounter"+"\n";
		
		
		objString +=indent+"queryID: 	"+query_id+"\n";
		objString +=indent+"hitCounter: "+hit_counter+"\n";
		return objString;
		
	}
	public String getQuery_id() {
		return query_id;
	}

	public int getHit_counter() {
		return hit_counter;
	}


}
