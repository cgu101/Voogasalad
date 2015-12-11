package network.util;

public enum ThreadType {
	JAVAFX("JavaFX Safe Thread"),
	DEFAULT("Standard Thread");
	
	private final String threadType;
	
	ThreadType (String threadType) {
		this.threadType = threadType;
	}
	
	@Override
	public String toString () {
		return threadType;
	}
}
