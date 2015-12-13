package network.framework.format;

public enum Request {
	GAME("Game"),
	NODE("Node"),
	DISCONNECT("Disconnect"),
	EXAMINE("Examine"),
	ADD("Add"), 
	DELETE("Delete"), 
	MODIFY("Modify"),
	LOAD("Load"),
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
