package mail;
 
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
 
public class TLSGmail {
 
    /**
       Outgoing Mail (SMTP) Server
       requires TLS or SSL: smtp.gmail.com (use authentication)
       Use Authentication: Yes
       Port for TLS/STARTTLS: 587
     */
    public static void sendGmail(String toEmail,String subject, String body){
        final String fromEmail = "alieng.cs.ecu@gmail.com"; //requires valid gmail id
        final String password = "alieng.cs@ecu"; // correct password for gmail id
         
         
        System.out.println("TLSEmail Start");
        try{
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
	        props.put("mail.smtp.port", "587"); //TLS Port
	        props.put("mail.smtp.auth", "true"); //enable authentication
	        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
	         
	        //create Authenticator object to pass in Session.getInstance argument
	        Authenticator auth = new Authenticator() {
	            //override the getPasswordAuthentication method
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(fromEmail, password);
	            }
	        };
	        Session session = Session.getInstance(props, auth);
	         
	        EmailUtil.sendEmail(session, toEmail,subject,body);
        }
        catch(Exception e)
        {
        	System.out.println("Error sending email...");
        	System.out.println("To Email: "+toEmail);
        	System.out.println("Subjectt: "+subject);
        	System.out.println("Body    : "+body);
        }
    }
 
     
}