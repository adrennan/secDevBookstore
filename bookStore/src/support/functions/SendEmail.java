package support.functions;

import java.util.*;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.util.Properties; 
import javax.mail.Message;
import javax.mail.MessagingException; 
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	
/**
 * Static method to send an email using the Dining Philosophers Bookstore gmail account, subject, target, text can be modified 
 * as needed
 * @param to The target email. Must be full email with host i.e. test@test.com
 * @param subject Subject of the email
 * @param text The text of the email
 */
	public static void send(String to, String subject, String text) {
	
		//properties to be passed with session
	    Properties props = new Properties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.debug", "false");
	    props.put("mail.smtp.port", "465");
	    props.put("mail.smtp.connectiontimeout", "t1");    
		props.put("mail.smtp.timeout", "t2");
	    props.put("mail.smtp.socketFactory.port", 465);
	    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.socketFactory.fallback", "false");
	    

	  
	    // Authorized the Session object with gmail account information
	    Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

	        @Override

	        protected PasswordAuthentication getPasswordAuthentication() {

	            return new PasswordAuthentication("DiningPhilosophersBookstore@gmail.com", "thisClass&ProjectLUL!");

	        }

	    });
	    
	    

	    InternetAddress fromAddress = null;
	    InternetAddress toAddress = null;
	    Transport transport = null;
	    
	    try {
	    	transport = mailSession.getTransport("smtp");
	 	    transport.connect();

	 	    //build message based on Session object
	 	    //May need to add in change to allow xml instead of text for the setText()
	        Message simpleMessage = new MimeMessage(mailSession);
	        fromAddress = new InternetAddress("DiningPhilosophersBookstore@gmail.com");
	        toAddress = new InternetAddress(to);
	        simpleMessage.setFrom(fromAddress);
	        simpleMessage.setRecipient(RecipientType.TO, toAddress);
	        simpleMessage.setSubject(subject);
	        simpleMessage.setText(text);
	        transport.sendMessage(simpleMessage,
	                simpleMessage.getAllRecipients());
	        transport.close();
	    }
	    catch (MessagingException e) {
	        e.printStackTrace();
	    } 
	}

}
