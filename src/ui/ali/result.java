package ui.ali;


import java.util.ArrayList;

import al.ali.mysql.MySQLAccess;

public class result {
	Integer ordNum;
	String user = null;
	String processStatus = null;
	
	
	public result(Integer ordNum,String user, 
			String status) {
		super();
		this.ordNum=ordNum;
		this.user = user;
		
		
		this.processStatus = status;
	}
	
	public void setOrdNum(Integer ordNum){
		this.ordNum=ordNum;
	}
	
	public Integer getOrdNum(){
		return this.ordNum;
	}
	
	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getProcessStatus() {
		return processStatus;
	}



	public static ArrayList<result> readResult(String user) throws Exception{
		ArrayList<result> res = null;
	    res = MySQLAccess.getResults(user);
		return res;
	}
}
