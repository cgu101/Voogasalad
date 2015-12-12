package network.core.connections.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import network.core.messages.Message;

public class SendThread extends ConnectionThread {

	private static final ThreadType threadType = ThreadType.SEND;
	private ObjectOutputStream out;
	
	public SendThread(Socket connection, BlockingQueue<Message> flowingMessages) 
			throws IOException {
		super(null, connection, flowingMessages);
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
		Message message = flowingMessages.take();
		out.writeObject(message);
	}

	@Override
	protected void resetStream() throws IOException {
		out = new ObjectOutputStream(connection.getOutputStream());
	}

}
