package network.core.messages;

public class IDMessageEncapsulation {

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
