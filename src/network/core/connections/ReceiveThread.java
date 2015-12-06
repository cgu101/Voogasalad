package network.core.connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import network.framework.format.Mail;

public class ReceiveThread extends ConnectionThread {
	
	private Integer id;
	private Socket connection;
	private BlockingQueue<Message> incomingMessages;
	private ObjectInputStream in;
	
	public ReceiveThread(Integer id, Socket connection, BlockingQueue<Message> incomingMessages) {
		this.id = id;
		this.connection = connection;
		this.incomingMessages = incomingMessages;
	}

	public void run() {
		
		try {
			in = new ObjectInputStream(connection.getInputStream());
			while (!closed) {
				try {
					Object message = in.readObject();
					Message msg = new Message(id, (Mail) message);
					incomingMessages.put(msg);
				}
				catch (InterruptedException e) {}
			}
		} catch (IOException e) {
			if (!closed) {
				System.out.println("Hub receive thread terminated by IOException: " + e);
			}
		} catch (Exception e) {
			if (!closed) {
				System.out.println("Unexpected error shuts down hub's receive thread: " + e);
			}
		}
	}
}
