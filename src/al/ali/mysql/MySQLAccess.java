package al.ali.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import ExtContentPath.UnivPathString;
import ui.ali.Config;
import ui.ali.result;
import filehandler.FileHandler;

public class MySQLAccess {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  
  
  public Connection newConnection() throws Exception{
	  Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
	  // System.out.printf("I am here! \n");
      /*Connection connect = DriverManager
          .getConnection("jdbc:mysql://localhost:3306/alieng_DB"
              + "user=root&password=1234");
      */
	  Connection connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/alieng_taxonomy_db_updated","root","1234");
	  return connect;
  }
  
  /*
  public void insertToDB(Connection connection, String...strings ) throws SQLException
  {
	  PreparedStatement ps = connection.prepareStatement("insert into id_to_name values (?, ?, ?, ?)");
	  ps.setString(1, strings[0]);
	  ps.setString(2, strings[1]);
	  ps.setString(3, strings[2]);
	  ps.setString(4, strings[3]);
	  //System.out.println(ps.toString());
	  ps.execute();
  }
  */
  public void insertToDB(Connection connect,String A) throws Exception{
	  
	  try{
		  

	     
	      // Statements allow to issue SQL queries to the database
	      statement = connect.createStatement();
	      PreparedStatement ps = connect.prepareStatement(A);
	      // Result set get the result of the SQL query
	     //statement.executeUpdate(A);
	      ps.execute();
	  }catch (Exception e) {
		  throw e;
	  } finally {
	      
		  close();
	  }
	  
  }
  
 public void insertToDB(Connection connect,String Table, String A,String B) throws SQLException{
	  
	 PreparedStatement ps = connect.prepareStatement("insert into " + Table + " values (?, ?)");
	  ps.setString(1, A);
	  ps.setString(2, B);
	  //System.out.println(ps.toString());
	  ps.execute();
	  
  }
 
 public void insertToDB(Connection connect,String Table, String A,String B, String C, String D, String E) throws SQLException{
	  
	 PreparedStatement ps = connect.prepareStatement("insert into " + Table + " values (?, ?, ?, ?, ?)");
	  ps.setString(1, A);
	  ps.setString(2, B);
	  ps.setString(3, C);
	  ps.setString(4, D);
	  ps.setString(5, E);

	  //System.out.println(ps.toString());
	  ps.execute();
	  
  }
 

	public String mapNameToId(Connection connect, String name) throws SQLException{
		String taxID = null;

		PreparedStatement ps = connect.prepareStatement("SELECT obj_id FROM name_to_id WHERE obj_name= ?");
		ps.setString(1, name);
		
		
		
		resultSet = ps.executeQuery();

		
		if(resultSet.next()){
			taxID = resultSet.getString("obj_id");
			return taxID;
			
		}
		return taxID;
	}

	public String getParent_IdToNode(Connection connect, String child_tax_id) throws SQLException{
		String result = null;
		
		PreparedStatement ps = connect.prepareStatement("SELECT parent_tax_id FROM id_to_node WHERE tax_id = ?");
		ps.setString(1, child_tax_id);
		resultSet = ps.executeQuery();

		
		if(resultSet.next()){
			result = resultSet.getString("parent_tax_id");
			return result;
			
		}
		return result;
	}

	public String mapIdToName(Connection connect, String taxId) throws SQLException{
		String name = null;

		PreparedStatement ps = connect.prepareStatement("SELECT name FROM id_to_name WHERE tax_id = ?");
		ps.setString(1, taxId);
		resultSet = ps.executeQuery();

		
		if(resultSet.next()){
			name = resultSet.getString("name");
			return name;
			
		}
		return name;
	}
	
	public ArrayList<String> get_id_to_node_dictionaryKeys(Connection connect) throws SQLException{
		PreparedStatement ps = connect.prepareStatement("SELECT tax_id FROM id_to_node");
		resultSet = ps.executeQuery();
		ArrayList<String> result = new ArrayList<String>();
		while(resultSet.next()){
			result.add(resultSet.getString("tax_id"));
		}
		return result;
	}
  
  public void close(Connection connect) {
	    try {
	      
	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {
	    }
	  }
  
  public boolean hasKey(Connection connect,String Table,String key) throws SQLException{
	  ResultSet resultSet = null;
	  //Statement a = connect.createStatement();
	  //String query = "select 1 from " + Table + " where name= '" + key + "'";
	  PreparedStatement ps = connect.prepareStatement("select 1 from " + Table + " where name= ?");
	  ps.setString(1, key);
	  
	  //resultSet = a.executeQuery(query);
	  resultSet = ps.executeQuery();

	  if (!resultSet.next())
		  return false;
	  return true;
  }
  
  public void readDataBase() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/feedback?"
              + "user=sqluser&password=sqluserpw");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from FEEDBACK.COMMENTS");
      writeResultSet(resultSet);

      // PreparedStatements can use variables and are more efficient
      preparedStatement = connect
          .prepareStatement("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
      // "myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
      // Parameters start with 1
      preparedStatement.setString(1, "Test");
      preparedStatement.setString(2, "TestEmail");
      preparedStatement.setString(3, "TestWebpage");
      preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
      preparedStatement.setString(5, "TestSummary");
      preparedStatement.setString(6, "TestComment");
      preparedStatement.executeUpdate();

      preparedStatement = connect
          .prepareStatement("SELECT myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
      resultSet = preparedStatement.executeQuery();
      writeResultSet(resultSet);

      // Remove again the insert comment
      preparedStatement = connect
      .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
      preparedStatement.setString(1, "Test");
      preparedStatement.executeUpdate();
      
      resultSet = statement
      .executeQuery("select * from FEEDBACK.COMMENTS");
      writeMetaData(resultSet);
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }

  private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // ResultSet is initially before the first data set
    while (resultSet.next()) {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
      String user = resultSet.getString("myuser");
      String website = resultSet.getString("webpage");
      String summery = resultSet.getString("summery");
      Date date = resultSet.getDate("datum");
      String comment = resultSet.getString("comments");
      System.out.println("User: " + user);
      System.out.println("Website: " + website);
      System.out.println("Summery: " + summery);
      System.out.println("Date: " + date);
      System.out.println("Comment: " + comment);
    }
  }

  // You need to close the resultSet
  private void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }
  
  public static ArrayList<result> getResults(String user) throws SQLException{
	    MySQLAccess newAccess=new MySQLAccess();
		ArrayList<result> ress = new ArrayList<result>();
		try{
			Connection newConnect=newAccess.newConnection();
			//Below must produce a string such as: "SELECT * from orders WHERE User like '%apil.tamang@gmail.com%'
			// where the leading and trailing '%' sign refers to any characters that may exist in the string.
			// It is included as such, because MySQL sometimes doesn't match my query when I exclude the '%' characters.
			String resultQuery=String.format("SELECT * from orders WHERE User like '%%%s%%'",user);
			PreparedStatement ps = newConnect.prepareStatement(resultQuery);
			ResultSet rs = ps.executeQuery();
	
			
			while(rs.next()){
				
				if(user.equals(rs.getString("User"))){			
					
					result j = new result(Integer.valueOf(rs.getInt("ordNum")),rs.getString("User"),
										  rs.getString("processStatus"));
	
					ress.add(j);
				}
				
			}
			return ress;
		}catch(Exception e){
			throw new SQLException(e.getMessage());
			
		}
  }
  
  public boolean validUser(Connection connect, String userName, String pass) throws SQLException{
	  PreparedStatement ps = connect.prepareStatement("select * from users where User=? and Password =? ");
	  ps.setString(1, userName);
	  ps.setString(2, pass);

		resultSet = ps.executeQuery();
		
		if(resultSet.next()) return true;
		else return false;
		
  }
  
  public boolean newUser(Connection connect, String userName) throws SQLException{
	  PreparedStatement ps = connect.prepareStatement("select * from users where User=?");
	  ps.setString(1, userName);

		resultSet = ps.executeQuery();
		
		if(!resultSet.next()) return true;
		else return false;
		
  }
  
  public String findUser(Connection connect,String userName) throws SQLException{
	  PreparedStatement ps = connect.prepareStatement("select Password from users where User=?");
	  ps.setString(1, userName);

		ResultSet rs = ps.executeQuery();
		
		if(!rs.next()) 
			return null;
		else 
			return rs.getString("Password");
	  
  }
  public void addUser(Connection connect, String userName, String pass, String firstname, String lastname) throws SQLException{
	  PreparedStatement ps = connect.prepareStatement("insert into users  values (?, ?,?,?)");
	  ps.setString(1, userName);
	  ps.setString(2, pass);
	  ps.setString(3, firstname);
	  ps.setString(4, lastname);

		ps.execute();

  }
  
  
  public void addRequest(Connection connect, String user, Config configFile) throws SQLException{
	  PreparedStatement ps = connect.prepareStatement("insert into orders (User, alienG_EvalueMax , alienG_Coverage , alienG_ScoreMin , alienG_group1 , alienG_group2 , alienG_AIEvalue , alienG_ratioScore , alienG_infile , alienG_exclusion , alienG_blastparam , alienG_blastoutput , processStatus) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
	  ps.setString(1, user);
	  ps.setString(2, String.valueOf(configFile.getAlienG_EvalueMax()));
	  ps.setString(3, String.valueOf(configFile.getAlienG_Coverage()));
	  ps.setString(4, String.valueOf(configFile.getAlienG_ScoreMin()));
	  ps.setString(5, configFile.getAlienG_group1());
	  ps.setString(6, configFile.getAlienG_group2());
	  ps.setString(7, configFile.getAlienG_AIEvalue());
	  ps.setString(8, configFile.getAlienG_ratioScore());
	  ps.setString(9, configFile.getAlienG_infile());
	  ps.setString(10, configFile.getAlienG_exclusion());
	  ps.setString(11, configFile.getAlienG_blastparam());
	  ps.setString(12, configFile.getAlienG_blastoutput());
	  
	  /*
	   * TODO: get count of orders before yours waiting to execute.
	   *
	   * String behindHowManyString="SELECT COUNT(*) FROM orders WHERE processStatus LIKE '%In%Queue%'"; //Match substring containing 'In Queue'
	   * PreparedStatement behindHowManyQuery=connect.prepareStatement(behindHowManyString);
	   * ResultSet rs=behindHowManyQuery.executeQuery();
	   * 
	  */
	  ps.setString(13,"In Queue");

	  ps.execute();

  }
  
  //========================================================================================================================  
  public Config getOnerequest(Connection connect,String option,int ordNum) throws SQLException{
	  
	  PreparedStatement ps=null;
	  String query=null;
	  if(option.equals("NewOrder"))
	  {
		  query = "select * from orders where  processStatus like '%In%Queue%' LIMIT 0, 1";
	  }
	  else if(option.equals("OrderDetails"))
	  {
	    query = String.format("select * from orders where  ordNum=%d",ordNum);
	  }
	  
	  ps=connect.prepareStatement(query);
	  Config res = new Config();

		resultSet = ps.executeQuery();
		  if(!resultSet.next()) return null;
		  
		  
		  else {
			  res.setUser(resultSet.getString("User"));
			  String status = resultSet.getString("processStatus");
			  res.setOrdNum(Integer.valueOf(resultSet.getInt("ordNum")));
			  res.setAlienG_AIEvalue(resultSet.getString("alienG_AIEvalue"));
			  			  
			  res.setAlienG_blastoutput(resultSet.getString("alienG_blastoutput"));
			  res.setAlienG_blastparam(resultSet.getString("alienG_blastparam"));
			  res.setAlienG_Coverage(Float.valueOf(resultSet.getString("alienG_Coverage")));
			  res.setAlienG_EvalueMax(Double.valueOf(resultSet.getString("alienG_EvalueMax")));
			  res.setAlienG_exclusion(resultSet.getString("alienG_exclusion"));
			  
			  res.setAlienG_group1(resultSet.getString("alienG_group1"));
			  res.setAlienG_group2(resultSet.getString("alienG_group2"));
			  res.setAlienG_infile(resultSet.getString("alienG_infile"));
			  
			  res.setAlienG_ratioScore(resultSet.getString("alienG_ratioScore"));
			  res.setAlienG_ScoreMin(Double.valueOf(resultSet.getString("alienG_ScoreMin")));
			  res.setProcessStatus(resultSet.getString("processStatus"));
			  
			  String homeDir=String.format("%s%s\\Order_%d\\",UnivPathString.rootPath,res.getUser(),res.getOrdNum());
			  res.setOrderHomeDir(homeDir);
			  
			  //return config object
			  return res;
		  }

  }
  //========================================================================================================================
  public static int modifyUserRequest(String username, String fileName){
	  /*
	   * this modifies the alieng_infile column in the 'orders' table.
	   * 
	   * This method is called right-after the addRequest method is called by Confirmation.jsp.
	   * The purpose is to get the ordNum value assigned
	   * to the most recent order by this user by MySQL (note: ordNum is an AUTO_INCREMENT key). Once, this value is
	   * retrieved, the alieng_infile string will be modified.
	   *  
	   * Also, a method is called that copies the file located in the user's home folder to a subdirectory specifying
	   * the order number specific to this order.
	   * ----------------------------------------------------------------------------------------------------------------
	   * Result of this operation
	   * a) alieng_infile attribute in orders table changed from
	   * 	- C:\Users\tamanga13\...\Thesis\User.email@domain.com --> C:\Users\tamanga13\...\User.email@domain.com\Order-N
	   * b) The user uploaded genome file: filename is moved from 
	   * 	- C:\Users\tamanga13\...\Thesis\User.email@domain.com --> C:\Users\tamanga13\...\User.email@domain.com\Order-N
	   * ----------------------------------------------------------------------------------------------------------------
	   */
	  
	  MySQLAccess newAccess=new MySQLAccess();
	  try{
		  int ordNum;
		  Connection myConnect=newAccess.newConnection();
		  String query=String.format("SELECT * from orders WHERE User like '%%%s%%' ORDER BY ordNum desc LIMIT 0,1",username);
		  PreparedStatement ps=myConnect.prepareStatement(query);
		  ResultSet rs=ps.executeQuery();
		  //if an order matching query is found
		  if(rs.next()){
			  ordNum=Integer.parseInt(rs.getString("ordNum")); 
			  String ordDir=String.format("Order_%d",ordNum);
			  //readies the sub-directory where all things 
			  //related to this user and order will be stored.

			  String srcDir=UnivPathString.rootPath+username;
			  String dstDir=UnivPathString.rootPath+username+"\\"+ordDir;

			  System.out.println("srcDir: "+srcDir);
			  System.out.println("dstDir: "+dstDir);
			  
			  //ready the order directory
			  FileHandler.readyDirectory(dstDir);
			  
			 /*
			  * Now copy the user uploaded file from 
			  * from  Thesis\some.name@email.com\alieng_infile
			  * to    Thesis\some.name@email.com\Order_X\alieng_infile
			  * 
			  */
			  
			  FileHandler.moveFile(srcDir, dstDir, fileName);
			  /* 
			   * Now update the alieng_infile field in the orders table to point to the
			   * new location: i.e. within the sub-directory that references the ordNum 
			   */
			  
			  String fileLoc=String.format("%s%s\\Order_%d\\%s",UnivPathString.rootPath,username,ordNum,fileName);
			  System.out.println(fileLoc);
			  fileLoc=fileLoc.replace("\\", "\\\\");
			  System.out.println(fileLoc);
			  
			  //query=String.format("UPDATE orders SET alienG_infile='%s' WHERE ordNum=%d",fileLoc,ordNum);	
			  
			  //update the location of the infile (also the blast output file)
			  
			  query=String.format("UPDATE orders SET "
			  		+ "alienG_infile='%s ' "			  					  		
			  		+ "WHERE ordNum=%d",fileLoc,ordNum);
			  
			  //now get a prepared statement and run query
			  ps=myConnect.prepareStatement(query);
			  //update the field in the sql table to point to the user uploaded infile
			  ps.executeUpdate();
			  
			  
			  return 0;
		  }

		  myConnect.close();
	  }catch(Exception e){
		  e.printStackTrace();
		  return 1;
	  }
	  
	  return 1;
	  //Now create the specific order subdirectory within the user's homedir
	  
	  
  }
  //========================================================================================================================
  public static void updateOrdersTable(String updateColumn, String updateMessage, int ordNum)throws SQLException{
	  /* updateMessage: Processing/Done
	   * ordNum  	  : identifies the row
	   * updateColumn : alienGStatus/phyloGStatus/processStatus
	   */
	  //Define connection
	  MySQLAccess newAccess=new MySQLAccess();
	  try {
		Connection myConnect=newAccess.newConnection();
    	String updateQueryString=String.format("UPDATE orders SET %s='%s' WHERE ordNum= %d",updateColumn,updateMessage,ordNum);
		PreparedStatement updateResultsQuery=myConnect.prepareStatement(updateQueryString);
		updateResultsQuery.execute();
		myConnect.close();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} //get connection from method defined in this class 
  }
  //========================================================================================================================
  public static void deleteRecordInOrdersTable(int ordNum){
	  /* 
	   * ordNum  	  : identifies the row
	   * 
	   */
	  //Define connection
	  MySQLAccess newAccess=new MySQLAccess();
	  try {
		Connection myConnect=newAccess.newConnection();
    	String updateQueryString=String.format("DELETE FROM orders WHERE ordNum= %d",ordNum);
		PreparedStatement updateResultsQuery=myConnect.prepareStatement(updateQueryString);
		updateResultsQuery.execute();
		myConnect.close();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} //get connection from method defined in this class 
  }

  //========================================================================================================================
  public static Config getOrderDetails(int ordNum){
	  /*---------------------------------------------
	   * This method is called by ViewOrderDetails.jsp
	   * page to display the detail of the order, 
	   * identified by the ordNum
	   * 
	   *---------------------------------------------*/
	  
	  MySQLAccess anAccess=new MySQLAccess();
	  try{
		  Connection aConnect=anAccess.newConnection();
		  String query=String.format("select * from orders where  ordNum=%d",ordNum);
		  PreparedStatement ps = aConnect.prepareStatement(query);
		  
		  Config res = new Config();
	
			ResultSet resultSet = ps.executeQuery();
			  if(!resultSet.next()) return null;
			  
			  
			  else {
				  res.setUser(resultSet.getString("User"));
				  String status = resultSet.getString("processStatus");
				  res.setOrdNum(Integer.valueOf(resultSet.getInt("ordNum")));
				  res.setAlienG_AIEvalue(resultSet.getString("alienG_AIEvalue"));
				  //res.setAlienG_blastdb(resultSet.getString("alienG_blastdb"));
				  res.setAlienG_blastdb(UnivPathString.val+ "nr\\nr");
				  res.setAlienG_blastoutput(resultSet.getString("alienG_blastoutput"));
				  res.setAlienG_blastparam(resultSet.getString("alienG_blastparam"));
				  res.setAlienG_Coverage(Float.valueOf(resultSet.getString("alienG_Coverage")));
				  res.setAlienG_EvalueMax(Double.valueOf(resultSet.getString("alienG_EvalueMax")));
				  res.setAlienG_exclusion(resultSet.getString("alienG_exclusion"));
				  res.setAlienG_formatdb(resultSet.getString("alienG_formatdb"));
				  res.setAlienG_group1(resultSet.getString("alienG_group1"));
				  res.setAlienG_group2(resultSet.getString("alienG_group2"));
				  res.setAlienG_infile(resultSet.getString("alienG_infile"));
				  res.setAlienG_numberOfTables(Integer.valueOf(resultSet.getString("alienG_numberOfTables")));
				  res.setAlienG_ratioScore(resultSet.getString("alienG_ratioScore"));
				  res.setAlienG_ScoreMin(Double.valueOf(resultSet.getString("alienG_ScoreMin")));
				  res.setProcessStatus(resultSet.getString("processStatus"));
				  
				  //return config object
				  return res;
			  }
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	  
	  return null;
  }
 
  
	//=============================================================================================================
	public static Config readOneRequest(String type,int ordNum) throws Exception{
		Config res;
		MySQLAccess sql = new MySQLAccess();
	    Connection connect=null;
	    connect = sql.newConnection();
	    
	    res = sql.getOnerequest(connect,type,ordNum);
	    //The -ve integer will issue a runtime error if I try to get a NewOrder request.
	    
	    if (res == null)
	    	;
	    else{
	    	System.out.println("na baba");
	    }
		
		return res;
	}

} 