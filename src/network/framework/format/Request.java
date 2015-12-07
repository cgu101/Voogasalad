package network.framework.format;

public enum Request {
	ADD("Add"), 
	DELETE("Delete"), 
	MODIFY("Modify"),
	TRANSITION("Transition");
	
	private final String requestType;
	
	Request (String request) {
		this.requestType = request;
	}
	
	@Override
	public String toString () {
		return requestType;
	}
}
