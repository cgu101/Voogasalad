package network.core.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.connections.ClientConnection;
import network.core.connections.NetworkObjectState;
import network.core.connections.threads.ConnectionThread;
import network.core.containers.NetworkContainer;
import network.core.messages.Message;
import network.core.messages.ServerMessage;
import network.framework.format.Request;

public class ConnectionController extends ConnectionThread {	
		
	private NetworkContainer<NetworkObjectState> games;
	private NetworkContainer<ClientConnection> clients;
	private BlockingQueue<ServerMessage> incomingMessages;
	private MessageHandler handler;
	
	public ConnectionController() {
		games = new NetworkContainer<NetworkObjectState>();
		clients = new NetworkContainer<ClientConnection>();
		incomingMessages = new LinkedBlockingQueue<ServerMessage>();
		handler = new MessageHandler();
	}
		
	@Override
	public void execute() {		
		try {
			ServerMessage sMsg = incomingMessages.take();	
			if(sMsg.getMessage() instanceof Message) {
				Message msg = (Message) sMsg.getMessage();
				handler.getHandler(msg.getRequest()).executeMessage(sMsg, clients, games);
			} else {
				ServerErrorController.sendErrorMessage(sMsg.getClientId(), clients, "Please send correct format");
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
		System.out.println("Hub just connected to: " + connection.getLocalAddress());

		try {
			String newId = IdManager.getNewClientId();
			ClientConnection toAdd = new ClientConnection(newId, incomingMessages, connection);
			toAdd.send(Request.CONNECTION, newId, null);
			clients.addObject(toAdd);
			
		} catch (IOException e) {
			System.out.println("Error when creating the client: " + e);
		}
	}
}
