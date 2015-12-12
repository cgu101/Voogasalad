package network.deprecated.threads;

import network.core.connections.ICloseable;

public abstract class ConnectionThread extends Thread implements ICloseable {
	
	private Boolean closed = false;
	
	/**
	 * The loop that will control the thread. Must override the execute method
	 * to add functionality to the thread
	 */
	@Deprecated
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
	
	@Deprecated
	public void close() {
		closed = true;
	}
	
	@Deprecated
	public Boolean isClosed() {
		return closed;	
	}
	
	@Deprecated
	public abstract void execute();

}
