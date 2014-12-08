package JUnitTests;

import static org.junit.Assert.*;

import al.ali.mysql.MySQLAccess;
import org.junit.Test;

public class test_OrdersTableModify {

	@Test
	public void test_1() {
		/* Make sure a folder 'username' exists.
		* Also, a file by name 'filename' exists
		* And, the orders table MUST have an order
		* for the given username, OR this test will
		* fail.
		*/
		String username="apil.tamang@gmail.com";
		String filename="test-movingfile1.txt";
		int done=MySQLAccess.modifyUserRequest(username,filename);
		assertEquals (done,0);

	}

}
