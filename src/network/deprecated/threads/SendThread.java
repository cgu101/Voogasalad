package network.deprecated.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.messages.Message;


public class SendThread extends ConnectionThread {

	private Socket connection;
	private LinkedBlockingQueue<Message> outGoingMessages;
	private ObjectOutputStream out;

	@Deprecated
	public SendThread(Socket connection, LinkedBlockingQueue<Message> outGoingMessages) throws IOException {
		this.connection = connection;
		this.outGoingMessages = outGoingMessages;
		this.out = new ObjectOutputStream(connection.getOutputStream());
	}
	
	@Deprecated
	@Override
	public void execute() {
		try {
			Message message = outGoingMessages.take();
			out.writeObject(message);
		} catch (IOException | InterruptedException e) {
			if(!isClosed()) {
				try {
					out = new ObjectOutputStream(connection.getOutputStream());
				} catch (IOException e1) {
					System.out.println("Failure trying to reopen outputstream: " + e);
					close();
				};
			}
		}	 
	}
	
	@Deprecated
	@Override
	public void close() {
		super.close();
		try {
			out.close();
			join(5);
		} catch(IOException | InterruptedException e) {
			System.out.println("Error while trying to close output stream: " + e);
		}
	}
}
