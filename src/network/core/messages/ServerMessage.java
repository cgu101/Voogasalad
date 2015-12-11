package network.core.messages;

public class ServerMessage {

	private String clientId;
	private Object message;
	
	public ServerMessage(String clientId, Object message) {
		this.clientId=clientId;
		this.message=message;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public Object getMessage() {
		return message;
	}
}
