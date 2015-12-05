package network.framework;

public enum Request {
	ADD("Add"), 
	DELETE("Delete"), 
	MODIFY("Modify");
	
	private final String requestType;
	
	Request (String request) {
		this.requestType = request;
	}
	
	@Override
	public String toString () {
		return requestType;
	}
}
