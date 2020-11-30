package org.apache.commons;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailConstants;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import junit.framework.TestCase;
import org.apache.commons.mail.EmailMock;

public class EmailTest extends TestCase{
	Email testEmail;
	
	public void setUp() {
		testEmail = new SimpleEmail();
		System.out.print(“mye631 - EmailTest class runs code coverage for 11 methods in Email class”);
	}
	
	public void testAddBcc() throws EmailException {
		testEmail.addBcc("a@b.com");
		assertEquals("a@b.com", testEmail.getBccAddresses().get(0).toString());
		String[] emails = new String[2];
		emails[0] = "b@c.com";
		emails[1] = "c@d.com";
		testEmail.addBcc(emails);
		assertEquals(3, testEmail.getBccAddresses().size());
		String[] emptyEmails = null;
		try {
			testEmail.addBcc(emptyEmails);
		}catch(Exception e) {
			assertEquals("Address List provided was invalid", e.getMessage());
		}
	}
	
	public void testAddCc() throws EmailException {
		testEmail.addCc("a@b.com");
		assertEquals("a@b.com", testEmail.getCcAddresses().get(0).toString());
		String[] emails = new String[2];
		emails[0] = "b@c.com";
		emails[1] = "c@d.com";
		testEmail.addCc(emails);
		assertEquals(3, testEmail.getCcAddresses().size());
		String[] emptyEmails = null;
		try {
			testEmail.addCc(emptyEmails);
		}catch(Exception e) {
			assertEquals("Address List provided was invalid", e.getMessage());
		}
	}
	
	
	public void testAddHeader(){
		try {
			testEmail.addHeader("", "Sendmail");
		}catch(Exception e) {
			assertEquals("name can not be null or empty", e.getMessage());
		}
		try {
			testEmail.addHeader("X-Mailer", "");
		}catch(Exception e) {
			assertEquals("value can not be null or empty", e.getMessage());
		}
		testEmail.addHeader("X-Mailer", "Sendmail");
	}
	
	public void testAddReplyTo() throws EmailException{
		testEmail.addReplyTo("a@b.com");
		assertEquals("a@b.com", testEmail.getReplyToAddresses().get(0).toString());
	}
	
	public void testSetFrom() throws EmailException, AddressException{
		testEmail.setFrom("test@user.com");
		assertEquals(new InternetAddress("test@user.com"), testEmail.getFromAddress());
	}
	
	public void testGetSentDate() {
		assertEquals(new Date(), testEmail.getSentDate());
		testEmail.setSentDate(new Date());
		assertEquals(new Date(), testEmail.getSentDate());
	}
	
	public void testGetSocketConnectionTimeout() {
		testEmail.setSocketConnectionTimeout(200);
		assertEquals(200, testEmail.getSocketConnectionTimeout());
	}
	
	public void testUpdateContentType() {
		testEmail.setCharset(EmailConstants.US_ASCII);
		testEmail.updateContentType(EmailConstants.TEXT_PLAIN);
		testEmail.updateContentType("text/html; charset=utf-8");
	}
	
	public void testBuildMimeMessage() throws EmailException,AddressException{
		Collection collection = new ArrayList();
	    collection.add(new InternetAddress("reply@user.com"));
		testEmail.setSubject("Main Subject");
		testEmail.setCharset("UTF-8");
		testEmail.setContent(new MimeMultipart());
		testEmail.setHostName(EmailConstants.MAIL_HOST);
		testEmail.setFrom("sender@user.com");
		testEmail.addCc("a@b.com");
		testEmail.addCc("b@c.com");
		testEmail.addTo("recepient@user.com");
		testEmail.setReplyTo(collection);
		testEmail.addHeader("X-Mailer", "Sendmail");
		testEmail.addBcc("c@d.com");
		testEmail.setMsg("This is a simple email message");
		testEmail.setPopBeforeSmtp(true, EmailConstants.MAIL_HOST, EmailConstants.MAIL_SMTP_USER, EmailConstants.MAIL_SMTP_PASSWORD);
		testEmail.setSSLOnConnect(true);
		try {
			testEmail.buildMimeMessage();
		}catch(EmailException e) {
			
		}
		
	}
	
	public void testSend() throws EmailException{
		EmailMock testEmail = new EmailMock();
		testEmail.setHostName("a.com");
		testEmail.setFrom("x@y.com");
		testEmail.addTo("y@z.com");
		testEmail.setSubject("My Subject");
		testEmail.send();
		assertEquals("My Subject", testEmail.getSent());
	}
	
	public void testGetHostName() throws MessagingException{
		Properties props = new Properties();
	    props.put(EmailConstants.MAIL_SMTP_FROM, "my-mail-server");
	    Session session = Session.getInstance(props, null);
	    testEmail.setHostName(EmailConstants.MAIL_HOST);
	    assertEquals("mail.smtp.host", testEmail.getHostName());
	    testEmail.setMailSession(session);
	    testEmail.getHostName();
	}
	
}
