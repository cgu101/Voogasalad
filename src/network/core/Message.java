package network.core;

import java.io.Serializable;

import network.framework.format.Request;

public class Message implements Serializable {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -7266468240235229064L;
	
	private Request request;
	private Serializable payload;
	private String id;
	
    public Message(Serializable payload, Request request, String id) {
    	this.request = request;
    	this.payload = payload;
    	this.id = id;
    }
    
    public Serializable getPaylad () {
    	return payload;
    }
    
    public Request getRequest () {
    	return request;
    }
    
    public String getID () {
    	return id;
    }
}
