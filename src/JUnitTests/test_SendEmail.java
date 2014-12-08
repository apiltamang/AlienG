package JUnitTests;

import static org.junit.Assert.*;
import mail.TLSGmail;

import org.junit.Test;

public class test_SendEmail {

	@Test
	public void test1() {
		   /**
	       Outgoing Mail (SMTP) Server
	       requires TLS or SSL: smtp.gmail.com (use authentication)
	       Use Authentication: Yes
	       Port for TLS/STARTTLS: 587
	     */
	      try {
			TLSGmail.sendGmail("apil.tamang@gmail.com","AlienG_Output is ready","Please log in to download results.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
