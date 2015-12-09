package network.core.connections.threads;

import network.core.connections.Closeable;

public abstract class ConnectionThread extends Thread implements Closeable {
	
	private Boolean closed = false;
	
	/**
	 * The loop that will control the thread. Must override the execute method
	 * to add functionality to the thread
	 */
	public void run() {
		while (!closed) {
			try {
				execute();
			}
			catch (Exception e) {
				System.out.println("Exception while handling received message: " + e);
			}
		}
	}
	
	public void close() {
		closed = true;
	}
	
	public Boolean isClosed() {
		return closed;	
	}
	
	public abstract void execute();

}
