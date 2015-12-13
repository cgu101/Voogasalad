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
import network.exceptions.StreamException;

public class ClientConnectionController extends AConnectionController<IClientExecuteHandler> {
	
	private static final String IP_ADDRESS = "wl-10-190-54-23.wireless.duke.edu";
	private static final Integer PORT = 5055;
	
	private Map<String, BlockingQueue<Message>> myQueues;
	private Connection clientConnection;
	
	private static final ClientConnectionController controller = new ClientConnectionController();
	
	private ClientConnectionController() {
		messageHandler = new ClientMessageHandler();
		myQueues = new HashMap<String, BlockingQueue<Message>>();
		try {
			handshake(new Socket(IP_ADDRESS, PORT));
		} catch(IOException e) {}
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
		// TODO better handshake
		System.out.println("Client just connected to: " + connection.getLocalAddress());

		try {
			clientConnection = new Connection(incomingMessages, connection);
			try {
				IDMessageEncapsulation msg = incomingMessages.take();
				handleMessage(msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			System.out.println("Error when creating the client: " + e);
		}
	}
	
	@Override
	protected void handleMessage(IDMessageEncapsulation message) {
		messageHandler.getHandler(message.getMessage().getRequest()).execute(message, clientConnection, myQueues);
	}

	@Override
	protected void closeStream() throws IOException {
		try {
			clientConnection.close();
		} catch (StreamException e) {
			// TODO handle closing exception
		}
	}

	@Override
	protected void resetStream() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
