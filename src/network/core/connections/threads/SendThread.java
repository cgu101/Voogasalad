package network.core.connections.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import network.deprecated.ForwardedMessage;
import network.framework.format.Mail;


public class SendThread extends ConnectionThread {

	private Socket connection;
	private LinkedBlockingQueue<Object> outGoingMessages;
	private ObjectOutputStream out;

	public SendThread(Socket connection, LinkedBlockingQueue<Object> outGoingMessages) throws IOException {
		this.connection = connection;
		this.outGoingMessages = outGoingMessages;
		this.out = new ObjectOutputStream(connection.getOutputStream());
	}
	
	@Override
	public void execute() {
		try {
			Object message = outGoingMessages.take();
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
