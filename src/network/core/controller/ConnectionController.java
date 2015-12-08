package network.core.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.Message;
import network.core.connections.ClientConnection;
import network.core.connections.NetworkGameState;
import network.core.connections.threads.ConnectionThread;
import network.core.containers.NetworkContainer;

public class ConnectionController extends ConnectionThread {	
		
	private NetworkContainer<NetworkGameState> games;
	private NetworkContainer<ClientConnection> clients;
	private BlockingQueue<Message> incomingMessages;
	private MessageHandler handler;
	
	public ConnectionController() {
		games = new NetworkContainer<NetworkGameState>();
		clients = new NetworkContainer<ClientConnection>();
		incomingMessages = new LinkedBlockingQueue<Message>();
		handler = new MessageHandler();
	}
		
	@Override
	public void execute() {		
		try {
			Message m = incomingMessages.take();
			handler.getHandler(m.getMail().getRequest()).executeMessage(m, clients, games);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		super.close();
		try {
			games.close();
			clients.close();
			join();
		} catch (InterruptedException e) {
			System.out.println("Error closing connection: " + e);
		}
	}
	
	public void addConnection(Socket connection) {
		handshake(connection);
	}
	
	private void handshake(Socket connection) {
		// TODO the actual handshake
		System.out.println("Hub just connected to: " + connection.getLocalAddress());
		
		// Then create the client
		try {
			clients.addObject(new ClientConnection(IdManager.getNewClientId(), incomingMessages, connection));
		} catch (IOException e) {
			System.out.println("Error when creating the client: " + e);
		}
	}
}
