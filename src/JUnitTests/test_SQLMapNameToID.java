package JUnitTests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import al.ali.blast.Timer;
import al.ali.mysql.MySQLAccess;

public class test_SQLMapNameToID {

	private static Connection connect;
	private static MySQLAccess sql;

	@BeforeClass
	public static void startUp()
	{
		sql=new MySQLAccess();
		try {
			connect=sql.newConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@AfterClass
	public static void tearDown() throws Exception 
	{
		connect.close();
	}


	@Test
	public void test() {
		String taxID=null;
		Timer tm1=new Timer();
		String hit_name="Shigella flexneri K-272";
		String solution="766148";
		tm1.startTimer();
		taxID=getTaxID(hit_name);
		tm1.stopTimer("SQL Query using =");
		System.out.println("Result taxID: "+taxID);
		assertTrue(taxID.equals(solution));
	}

	public String getTaxID(String hit_name)
	{
		String result;
		try {
			
			result = sql.mapNameToId(connect, hit_name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="foobar";
		}
		return result;
		
	}
	
	@Test
	public void test2 ()
	{
		String taxID=null;
		Timer tm1=new Timer();
		String hit_name="Agrobacterium sp. AGR17";
		String solution="768908";
		tm1.startTimer();
		taxID=getTaxID(hit_name);
		tm1.stopTimer("SQL Query using =");
		System.out.println("Result taxID: "+taxID);
		assertTrue(taxID.equals(solution));

	}
	
	@Test
	public void test3()
	{
		String taxId="32";
		String name=null;
		String soln="Myxococcus";
		try {
			name=sql.mapIdToName(connect, taxId);
			System.out.println("Name returned: "+name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(soln,name);
	}

}
