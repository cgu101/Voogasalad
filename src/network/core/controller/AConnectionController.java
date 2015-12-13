package network.core.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.connections.threads.ConnectionThread;
import network.core.connections.threads.ThreadType;
import network.core.messages.IDMessage;

/**
 * 
 * @author Chris Streiffer (cds33)
 *
 */

public abstract class AConnectionController extends ConnectionThread {
	
	private static final ThreadType threadType = ThreadType.CONTROLLER;
	protected BlockingQueue<IDMessage> incomingMessages;
	
	public AConnectionController() {
		incomingMessages = new LinkedBlockingQueue<IDMessage>();
	}
	
	@Override
	protected ThreadType getThreadType() {
		return threadType;
	}
	
	@Override
	protected void executeUsingStream() throws IOException, InterruptedException, ClassNotFoundException {
		IDMessage message = incomingMessages.take();
		handleMessage(message);
	}
	
	protected abstract void handshake(Socket connection);
	
	protected abstract void handleMessage(IDMessage message);

}
