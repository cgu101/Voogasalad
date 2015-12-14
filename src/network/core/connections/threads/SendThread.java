// This entire file is part of my masterpiece.
// Christopher Streiffer
package network.core.connections.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import network.core.messages.IDMessageEncapsulation;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class SendThread extends ConnectionThread {

	private static final ThreadType threadType = ThreadType.SEND;
	private ObjectOutputStream out;
	
	public SendThread(Socket connection, BlockingQueue<IDMessageEncapsulation> flowingMessages) 
			throws IOException {
		super(connection, flowingMessages);
		this.out = new ObjectOutputStream(connection.getOutputStream());
	}

	@Override
	protected void closeStream() throws IOException {
		out.close();
	}

	@Override
	protected ThreadType getThreadType() {
		return threadType;
	}

	@Override
	protected void executeUsingStream() throws IOException, InterruptedException, ClassNotFoundException {
		IDMessageEncapsulation message = flowingMessages.take();
		out.writeObject(message);
	}

	@Override
	protected void resetStream() throws IOException {
		out = new ObjectOutputStream(connection.getOutputStream());
	}

}
