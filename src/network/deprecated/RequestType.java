package network.deprecated;

@Deprecated
public enum RequestType {
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
	
	@Deprecated
	RequestType (String request) {
		this.requestType = request;
	}
	
	@Deprecated
	@Override
	public String toString () {
		return requestType;
	}
}
