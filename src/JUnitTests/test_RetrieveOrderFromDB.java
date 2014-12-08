package JUnitTests;
import java.sql.Connection;
import java.sql.SQLException;

import al.ali.mysql.*;
import static org.junit.Assert.*;

import java.sql.PreparedStatement;

import org.junit.Test;


import ui.ali.Config;

public class test_RetrieveOrderFromDB {

	@Test
	public void test() {
		/* WARNING: This will erase all entries in the orders table */
		
		//set up a new connection
		MySQLAccess sql = new MySQLAccess();
	    Connection connect=null;
	    PreparedStatement ps;
	    try {//insert records
			connect = sql.newConnection();
			ps=connect.prepareStatement("TRUNCATE TABLE orders");
			ps.execute();
			
			ps=connect.prepareStatement("insert into orders(User,AlienG_Coverage,alienG_EvalueMax,AlienG_numberOfTables,status,AlienG_ScoreMin)  "
					+ "values('UserA','1.0','1.01E-4','1','Waiting To be Proceed','1.01E-4')");
			ps.execute();
			
			ps=connect.prepareStatement("insert into orders(User,AlienG_Coverage,alienG_EvalueMax,AlienG_numberOfTables,status,AlienG_ScoreMin)  "
					+ "values('UserA','1.0','1.01E-4','2','Waiting To be Proceed','1.01E-4')");
			ps.execute();
			
			ps=connect.prepareStatement("insert into orders(User,AlienG_Coverage,alienG_EvalueMax,AlienG_numberOfTables,status,AlienG_ScoreMin)  "
					+ "values('UserA','1.0','1.01E-4','3','Waiting To be Proceed','1.01E-4')");
			ps.execute();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.printf("SQL exception: %s", e.getMessage());
			e.printStackTrace();
		}	
	    
	    //Now retrieve Config objects from the 'orders' table, deleting the record
	    //each time they are retrieved.	    
		Config res=new Config();
	    int iter=0;
		try {
			res = sql.getOnerequest(connect,"NewOrder",-999); //This always only retrieves the very first record
			//because as soon as a record is read, the method deletes the record from the table.
		    while (res != null){
		    
		    	System.out.println("Retrieving record");
		    	//Test the retrieved data:
		    	// FIFO (first-in => first-out) data structure
		    	//1st entry match:
		    	if(iter==0)assertEquals(res.getAlienG_numberOfTables(),1);
		    	
		    	//2nd entry match:
		    	if(iter==1)assertEquals(res.getAlienG_numberOfTables(),2);
		    	
		    	//3rd entry match:
		    	if(iter==2)assertEquals(res.getAlienG_numberOfTables(),3);
		    	
		    	iter=iter+1;
				res = sql.getOnerequest(connect,"NewOrder",-999); //This always only retrieves the very first record
		    }
	    	assertEquals(3,iter); //This is to check that 3 iterations happened!
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.printf("Exception Caught: %s",e.getMessage());
			e.printStackTrace();
		}
	    

	}

}
