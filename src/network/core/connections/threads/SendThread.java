package network.core.connections.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import network.framework.format.Mail;


public class SendThread extends ConnectionThread {

	private Socket connection;
	private LinkedBlockingQueue<Mail> outGoingMessages;
	private ObjectOutputStream out;

	public SendThread(Socket connection, LinkedBlockingQueue<Mail> outGoingMessages) throws IOException {
		this.connection = connection;
		this.outGoingMessages = outGoingMessages;
		this.out = new ObjectOutputStream(connection.getOutputStream());
	}
	
	@Override
	public void execute() {
		try {
			Mail message = outGoingMessages.take();
			out.writeObject(message);
		} catch (IOException | InterruptedException e) {
			if(!isClosed()) {
				try {
					out = new ObjectOutputStream(connection.getOutputStream());
				} catch (IOException e1) {
					System.out.println("Failure trying to reopen outputstream: " + e);
				};
			}
		}	 
	}
	
	@Override
	public void close() {
		super.close();
		try {
			out.close();
		} catch(IOException e) {
			System.out.println("Error while trying to close output stream: " + e);
		}
	}
}
