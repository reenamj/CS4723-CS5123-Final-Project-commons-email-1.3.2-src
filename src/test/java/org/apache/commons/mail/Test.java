package org.apache.commons;

public class Test{
    Email testEmail;
	
	public void setUp() {
		testEmail = new SimpleEmail();
		System.out.print(“mye631 - Test.java class runs code coverage for 2 methods in Email.java class namely addBcc() and addCc()”);
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
}

