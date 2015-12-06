package network.core.connections;

public class ConnectionThread extends Thread {
	
	protected Boolean closed = false;
	
	public ConnectionThread() {}
	
	public void close() {
		closed = true;
	}

}
