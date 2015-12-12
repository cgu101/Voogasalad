package network.core.connections.threads;

public enum ThreadType {
	SEND("Send Thread"),
	RECEIVE("Receive Thread");
	
	private final String threadType;
	
	ThreadType (String threadType) {
		this.threadType = threadType;
	}
	
	@Override
	public String toString () {
		return threadType;
	}
}
