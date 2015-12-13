package network.core.connections.threads;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import network.core.connections.ICloseable;
import network.core.connections.IDistinguishable;
import network.core.connections.IExecutable;
import network.core.messages.Message;
import network.exceptions.StreamException;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public abstract class ConnectionThread extends Thread implements IExecutable, ICloseable, IDistinguishable {

	private static final long THREAD_DIE_TIME = 5; //millis
	private static final String REOPEN_ERROR_MESSAGE = "Failure trying to reopen from: ";
	
	private Boolean closed = false;

	private String id;
	protected Socket connection;
	protected BlockingQueue<Message> flowingMessages;

	public ConnectionThread (String id, Socket connection, BlockingQueue<Message> flowingMessages) {
		this.id = id;
		this.connection = connection;
		this.flowingMessages = flowingMessages;
	}
	
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

	@Override
	public void close() throws StreamException {
		this.closed = true;

		try {
			closeStream();
			join(THREAD_DIE_TIME);
		} catch(IOException | InterruptedException e) {
			throw new StreamException(getThreadType());
		}
	}

	@Override
	public Boolean isClosed() {
		return this.closed;
	}

	public String getID () {
		return this.id;
	}

	@Override
	public void execute() throws StreamException {
		try {
			executeUsingStream();
		} catch (IOException | InterruptedException | ClassNotFoundException e) {
			if(!isClosed()) {
				try {
					resetStream();
				} catch (IOException e1) {
					System.out.println(REOPEN_ERROR_MESSAGE + getThreadType().toString());
					close();
				};
			}
		}	 
	}

	protected abstract void closeStream() throws IOException;
	protected abstract ThreadType getThreadType();
	protected abstract void executeUsingStream() throws IOException, InterruptedException, ClassNotFoundException;
	protected abstract void resetStream() throws IOException;
}
