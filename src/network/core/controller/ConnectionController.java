package network.core.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import network.core.connections.Connection;
import network.core.connections.NetworkObjectState;
import network.core.connections.threads.ConnectionThread;
import network.core.connections.threads.ThreadType;
import network.core.containers.NetworkContainer;
import network.core.messages.Message;
import network.core.messages.format.Request;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class ConnectionController extends ConnectionThread {	
		
	private static final ThreadType threadType = ThreadType.RECEIVE;
	
	private NetworkContainer<NetworkObjectState> games;
	private NetworkContainer<Connection> clients;
	private BlockingQueue<Message> incomingMessages;
	private MessageHandler handler;
	
	public ConnectionController(String id, Socket connection, BlockingQueue<Message> flowingMessages) {
		super(id, connection, flowingMessages); //TODO
		
		games = new NetworkContainer<NetworkObjectState>();
		clients = new NetworkContainer<Connection>();
		incomingMessages = flowingMessages;
		handler = new MessageHandler();
	}
		
	
	//TODO
//	@Override
//	public void execute() {		
//		try {
//			Message msg = incomingMessages.take();	
//			handler.getHandler(msg.getRequest()).executeMessage(msg, clients, games);
//			ServerErrorController.sendErrorMessage(msg.getID(), clients, "Please send correct format");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
//	@Override
//	public void close() {
//		super.close();
//		try {
//			games.close();
//			clients.close();
//			join();
//		} catch (Exception e) {
//			System.out.println("Error closing connection: " + e);
//		}
//	}
	
	public void addConnection(Socket connection) {
		handshake(connection);
	}
	
	private void handshake(Socket connection) {
		System.out.println("Hub just connected to: " + connection.getLocalAddress());

		try {
			String newId = IdManager.getNewClientId();
			Connection toAdd = new Connection(newId, incomingMessages, connection); //TODO
			toAdd.send(Request.CONNECTION, newId, null);
			clients.addObject(toAdd);
			
		} catch (IOException e) {
			System.out.println("Error when creating the client: " + e);
		}
	}

	@Override
	protected void closeStream() {
		try {
			games.close();
			clients.close();
		} catch (Exception e) {
			System.out.println("Error closing connection: " + e);
		}
	}

	@Override
	protected ThreadType getThreadType() {
		return threadType;
	}

	@Override
	protected void executeUsingStream() throws IOException, InterruptedException, ClassNotFoundException {
		Message msg = incomingMessages.take();	
		handler.getHandler(msg.getRequest()).executeMessage(msg, clients, games);
		ServerErrorController.sendErrorMessage(msg.getID(), clients, "Please send correct format");
	}

	@Override
	protected void resetStream() throws IOException {
		//TODO
	}
}
