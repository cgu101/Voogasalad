package network.core.messages;

import java.io.Serializable;

public class IDMessageEncapsulation implements Serializable {

	private String clientId;
	private Message message;
	
	public IDMessageEncapsulation(String clientId, Message message) {
		this.clientId=clientId;
		this.message=message;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public Message getMessage() {
		return message;
	}
}
