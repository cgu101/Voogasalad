package network.util;

public enum ConnectionType {
	JAVAFX("JavaFX Safe Connection"),
	DEFAULT("Standard Connection");
	
	private final String connectionType;
	
	ConnectionType (String connectionType) {
		this.connectionType = connectionType;
	}
	
	@Override
	public String toString () {
		return connectionType;
	}
}
