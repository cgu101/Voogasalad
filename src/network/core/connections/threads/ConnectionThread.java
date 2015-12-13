package network.core.connections.threads;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import network.core.connections.ICloseable;
import network.core.connections.IDistinguishable;
import network.core.connections.IExecutable;
import network.core.messages.IDMessageEncapsulation;
import network.exceptions.StreamException;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Describes a thread of execution of a program specific to client-server communication.
 * Multiple threads are allowed to be run at the same time.
 * Describes the parent class, whose children are Receive and Send.
 */

public abstract class ConnectionThread extends Thread implements IExecutable, ICloseable, IDistinguishable {

	private static final long THREAD_DIE_TIME = 5; //millis
	private static final String REOPEN_ERROR_MESSAGE = "Failure trying to reopen from: ";
	
	private Boolean closed = false;

	private String id;
	protected Socket connection;
	protected BlockingQueue<IDMessageEncapsulation> flowingMessages;
	
	public ConnectionThread() {
		
	}
	
	public ConnectionThread (Socket connection, BlockingQueue<IDMessageEncapsulation> flowingMessages) {
		this(ConnectionThread.class.getName(), connection, flowingMessages);
	}
	

	public ConnectionThread (String id, Socket connection, BlockingQueue<IDMessageEncapsulation> flowingMessages) {
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

	/**
	 * Method described that closes the stream associated with this thread
	 * @throws IOException
	 */
	protected abstract void closeStream() throws IOException;
	
	/**
	 * Returns an enum indicating the type of thread this class represents
	 * @return
	 */
	protected abstract ThreadType getThreadType();
	
	/**
	 * Executes functionalities using the stream associated with this thread
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	protected abstract void executeUsingStream() throws IOException, InterruptedException, ClassNotFoundException;
	
	/**
	 * Resets the stream under the circumstance that the stream is not closed when it should be
	 * 
	 * @throws IOException
	 */
	protected abstract void resetStream() throws IOException;
}
