// This entire file is part of my masterpiece.
// Austin Liu

package network.core.messages;

import java.io.Serializable;

public class IDMessageEncapsulation implements Serializable {

	private static final long serialVersionUID = 4480245812681171848L;
	
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
