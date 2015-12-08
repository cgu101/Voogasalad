package network.core;

import network.framework.format.Mail;

public class Message {
	
    private String client;
    private Mail mail;
    
    public Message(String client, Mail mail) {
    	this.client = client;
    	this.mail = mail;
    }
    
    public Mail getMail() {
    	return mail;
    }
    
    public String getClient() {
    	return client;
    }
}
