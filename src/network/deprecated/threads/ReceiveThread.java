package network.deprecated.threads;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import network.core.messages.Message;
import network.core.messages.IDMessage;

public class ReceiveThread extends ConnectionThread {
	
	private Socket connection;
	private String id;
	private BlockingQueue<IDMessage> incomingMessages;
	private ObjectInputStream in;
	
	@Deprecated
	public ReceiveThread(String id, Socket connection, BlockingQueue<IDMessage> incomingMessages) throws IOException {
		this.id = id;
		this.connection = connection;
		this.incomingMessages = incomingMessages;
		in = new ObjectInputStream(connection.getInputStream());
	}
	
	@Deprecated
	@Override
	public void execute() {
//		try {
//			//Message message = in.readObject();
//			//ServerMessage temp = new ServerMessage(id, message);
//			//incomingMessages.put(temp);
//		}
//		catch (InterruptedException | ClassNotFoundException | IOException e) {
//			if(!isClosed()) {
//				try {
//					in = new ObjectInputStream(connection.getInputStream());
//				} catch (IOException e1) {
//					System.out.println("Failure trying to ropen connection: " + e);
//					close();
//				};
//			}
//		}
	}
	
	@Deprecated
	@Override
	public void close() {
		super.close();
		try {
			in.close();
			join(5);
		} catch(IOException | InterruptedException e) {
			System.out.println("Error while trying to close input stream: " + e);
		}
	}
}
