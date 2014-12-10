package ui.ali;

import java.sql.Connection;
import al.ali.mysql.MySQLAccess;

public class user {
	private String userName;
	 private String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static String findUser(String userName) throws Exception{
		MySQLAccess sql=new MySQLAccess();
		Connection connect=sql.newConnection();
		String pass=sql.findUser(connect, userName);
		connect.close();
		return pass;
		
	}
	public static boolean checkUser(String userName, String pass) throws Exception{
		MySQLAccess sql = new MySQLAccess();
	    Connection connect=null;
	    connect = sql.newConnection();
	    
	    boolean res = sql.validUser(connect, userName, pass);
	    connect.close();
		return res;
	}
	public static boolean newUser(String userName) throws Exception{
		MySQLAccess sql = new MySQLAccess();
	    Connection connect=null;
	    connect = sql.newConnection();
	    
	    boolean res = sql.newUser(connect, userName);
	    connect.close();
		return res;
	}
	
	public static void addUser(String userName, String pass,String firstname,String lastname) throws Exception{
		MySQLAccess sql = new MySQLAccess();
	    Connection connect=null;
	    connect = sql.newConnection();
	    
	    sql.addUser(connect, userName, pass,firstname,lastname);
	    connect.close();		
	}
}
