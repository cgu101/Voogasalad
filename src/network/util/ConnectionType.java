// This entire file is part of my masterpiece.
// Austin Liu

package network.util;


/**
 * @author Austin and Chris Streiffer
 */

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
