package al.ali.main;

public class fillTheDataBase {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try{
			NCBIToDB a = new NCBIToDB();
			a.fill_names_db();
			System.out.println("Successfully created db");
		}
		catch (Exception e){
			System.out.printf("Unable to do so! Exception error: %s \n",e.getMessage());
			System.exit(1);			
		}
	}

}
