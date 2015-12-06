package network.core.connections;

import java.util.concurrent.LinkedBlockingQueue;

import network.game.Message;

public class MessageHandlerThread extends ConnectionThread {

	private LinkedBlockingQueue<Message> incomingMessages;
	public MessageHandlerThread(LinkedBlockingQueue<Message> incomingMessages) {
		this.incomingMessages = incomingMessages;
	}

	public void run() {
		while (!closed) {
			try {
				Message msg = incomingMessages.take();  
				// Need to do something here
			}
			catch (Exception e) {
				System.out.println("Exception while handling received message: " + e);
			}
		}
	}
	
	public void addConnectionToClient(ConnectionToClient cli) {
		
	}
}