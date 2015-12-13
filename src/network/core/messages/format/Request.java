package network.core.messages.format;

/**
 * @author Austin Liu (abl17) and Chris Streiffer (cds33)
 *
 */
public enum Request {
	ERROR("Error"),
	CONNECTION("Connetion"),
	LOADGROUP("Load Group"),
	CREATEGROUP("Create Group"),
	QUEUEDATA("Queue Data"),
	STATE("State");
	
	private final String requestType;
	
	Request (String request) {
		this.requestType = request;
	}
	
	@Override
	public String toString () {
		return requestType;
	}
}
