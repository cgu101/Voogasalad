package network.core.messages;

public class IDMessage {

	private String clientId;
	private Message message;
	
	public IDMessage(String clientId, Message message) {
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
