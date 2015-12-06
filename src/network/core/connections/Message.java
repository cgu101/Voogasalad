package network.core.connections;

import network.framework.format.Mail;

public class Message {
	
    private Integer client;
    private Mail mail;
    
    public Message(Integer client, Mail mail) {
    	this.client = client;
    	this.mail = mail;
    }
    
    public Mail getMail() {
    	return mail;
    }
    
    public Integer getConnectionToClient() {
    	return client;
    }
}
