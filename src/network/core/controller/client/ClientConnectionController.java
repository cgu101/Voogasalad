package network.core.controller.client;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import network.core.connections.Connection;
import network.core.connections.ISendable;
import network.core.controller.AConnectionController;
import network.core.messages.IDMessageEncapsulation;
import network.core.messages.Message;
import network.core.messages.format.Request;

public class ClientConnectionController extends AConnectionController {
	
	private static final String IP_ADDRESS = "localhost";
	private static final Integer PORT = 5055;
	
	private Map<String, BlockingQueue<Message>> myQueues;
	private ISendable clientConnection;
	
	private static final ClientConnectionController controller = new ClientConnectionController();
	
	private ClientConnectionController() {
		myQueues = new HashMap<String, BlockingQueue<Message>>();
		try {
			handshake(new Socket(IP_ADDRESS, PORT));
		} catch(IOException e) {
			// TODO Handle this exception
		}
	}
	
	public static ClientConnectionController getInstance() {
		return controller;
	}
	
	public void addQueue(String queueId, BlockingQueue<Message> queue) {
		myQueues.put(queueId, queue);
	}
	
	public ISendable getConnection() {
		return clientConnection;
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
	
	@Override
	protected void handleMessage(IDMessageEncapsulation message) {
		Message msg = message.getMessage();
		if(msg.getRequest() == Request.QUEUEDATA) {
			if(myQueues.containsKey(msg.getID())) {
				myQueues.get(msg.getID()).add(msg);
			} else {
				// TODO Send error to the server or something
				System.out.println("Invalid queue identifier received from server");
			}
		}
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
