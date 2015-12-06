package network.core.connections;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.MessageController;
import network.core.Hub.Message;

public class MessageHandlerThread extends ConnectionThread {

	private LinkedBlockingQueue<Message> incomingMessages;


	public MessageHandlerThread(LinkedBlockingQueue<Message> incomingMessages) {
		this.incomingMessages = incomingMessages;
	}

	public void run() {
		while (!closed) {
			try {
				Message msg = incomingMessages.take();              
			}
			catch (Exception e) {
				System.out.println("Exception while handling received message: " + e);
			}
		}
	}
}