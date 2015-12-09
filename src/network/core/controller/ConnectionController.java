package network.core.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.ForwardedMessage;
import network.core.Message;
import network.core.connections.ClientConnection;
import network.core.connections.NetworkGameState;
import network.core.connections.threads.ConnectionThread;
import network.core.containers.NetworkContainer;
import network.framework.format.Mail;

public class ConnectionController extends ConnectionThread {	
		
	private NetworkContainer<NetworkGameState> games;
	private NetworkContainer<ClientConnection> clients;
	private BlockingQueue<Object> incomingMessages;
	private MessageHandler handler;
	
	public ConnectionController() {
		games = new NetworkContainer<NetworkGameState>();
		clients = new NetworkContainer<ClientConnection>();
		incomingMessages = new LinkedBlockingQueue<Object>();
		handler = new MessageHandler();
	}
		
	@Override
	public void execute() {		
		try {
			Object m = incomingMessages.take();
			if(m instanceof ForwardedMessage) {
				ForwardedMessage m1 = (ForwardedMessage) m;
				if(m1.message instanceof Mail) {
					handler.getHandler(((Mail) m1.message).getRequest()).executeMessage(m1, clients, games);
				}
			}
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
			Integer val = IdManager.getNewClientId();
			ClientConnection toAdd = new ClientConnection(val, incomingMessages, connection);
			toAdd.send(val);
			clients.addObject(toAdd);
			
		} catch (IOException e) {
			System.out.println("Error when creating the client: " + e);
		}
	}
}
