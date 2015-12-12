package network.core.connections.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import network.core.messages.Message;

public class ReceiveThread extends ConnectionThread {

	private static final ThreadType threadType = ThreadType.RECEIVE;
	
	private ObjectInputStream in;

	public ReceiveThread(String id, Socket connection, BlockingQueue<Message> flowingMessages)
			throws IOException {
		super(id, connection, flowingMessages);
		this.in = new ObjectInputStream(connection.getInputStream());
	}

	@Override
	protected void closeStream() throws IOException {
		in.close();
	}

	@Override
	protected ThreadType getThreadType() {
		return threadType;
	}

	@Override
	protected void executeUsingStream() throws IOException, InterruptedException, ClassNotFoundException {
		Message payload = (Message) in.readObject();
		flowingMessages.put(payload);
	}

	@Override
	protected void resetStream() throws IOException {
		in = new ObjectInputStream(connection.getInputStream());
	}

}
