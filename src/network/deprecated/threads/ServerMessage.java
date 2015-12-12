package network.deprecated.threads;

public class ServerMessage {

	private String clientId;
	private Object message;
	
	@Deprecated
	public ServerMessage(String clientId, Object message) {
		this.clientId=clientId;
		this.message=message;
	}
	
	@Deprecated
	public String getClientId() {
		return clientId;
	}
	
	@Deprecated
	public Object getMessage() {
		return message;
	}
}
