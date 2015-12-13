package network.core.controller.client;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import network.core.connections.Connection;
import network.core.controller.AConnectionController;
import network.core.messages.IDMessage;
import network.core.messages.Message;

public class ClientConnectionController extends AConnectionController {
	
	private static final String IP_ADDRESS = "localhost";
	private static final Integer PORT = 5055;
	
	private Map<String, BlockingQueue<Message>> myQueues;
	private Connection clientConnection;
	
	private static final ClientConnectionController controller = new ClientConnectionController();
	
	private ClientConnectionController() {
		myQueues = new HashMap<String, BlockingQueue<Message>>();
		try {
			handshake(new Socket(IP_ADDRESS, PORT));
		} catch(IOException e) {
			// TODO Handle this exception
		}
	}
	
	@Override
	protected void handshake(Socket connection) {
		System.out.println("Client just connected to: " + connection.getLocalAddress());

		try {
			clientConnection = new Connection(incomingMessages, connection);
		} catch (IOException e) {
			System.out.println("Error when creating the client: " + e);
		}
	}
	
	public void addQueue(String queueId, BlockingQueue<Message> queue) {
		myQueues.put(queueId, queue);
	}
	
	@Override
	protected void handleMessage(IDMessage message) {
		
	}

	@Override
	protected void closeStream() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void resetStream() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
