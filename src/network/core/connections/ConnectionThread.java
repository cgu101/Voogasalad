package network.core.connections;

public abstract class ConnectionThread extends Thread {
	
	protected Boolean closed = false;
	
	public void close() {
		closed = true;
	}

}
