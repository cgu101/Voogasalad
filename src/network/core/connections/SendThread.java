package network.core.connections;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import network.framework.format.Mail;


public class SendThread extends ConnectionThread {

	private Socket connection;
	private LinkedBlockingQueue<Mail> outGoingMessages;
	private ObjectOutputStream out;

	public SendThread(Socket connection, LinkedBlockingQueue<Mail> outGoingMessages) {
		this.connection = connection;
		this.outGoingMessages = outGoingMessages;
	}


	public void run() {
		try {
			out = new ObjectOutputStream(connection.getOutputStream());
			
			while ( !closed ) { 
				try {
					Mail message = outGoingMessages.take();
					out.writeObject(message);	                
				} catch (InterruptedException e) {}
			}    
		} catch (IOException e) {
			if (!closed) {
				System.out.println("Hub send thread terminated by IOException: " + e);
			}
		} catch (Exception e) {
			if (!closed) {
				System.out.println("Unexpected error shuts down hub's send thread: " + e);
			}
		}
	}
}
