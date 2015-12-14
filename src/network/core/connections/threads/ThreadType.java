// This entire file is part of my masterpiece.
// Christopher Streiffer
package network.core.connections.threads;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Enum describing the type of thread.
 */

public enum ThreadType {
	SEND("Send Thread"),
	RECEIVE("Receive Thread"),
	CONTROLLER("Control Thread");
	
	private final String threadType;
	
	ThreadType (String threadType) {
		this.threadType = threadType;
	}
	
	@Override
	public String toString () {
		return threadType;
	}
}
