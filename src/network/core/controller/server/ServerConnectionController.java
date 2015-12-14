// This entire file is part of my masterpiece.
// Christopher Streiffer
package network.core.controller.server;

import java.io.IOException;
import java.net.Socket;

import network.core.connections.Connection;
import network.core.connections.NetworkState;
import network.core.containers.NetworkContainer;
import network.core.controller.AConnectionController;
import network.core.messages.IDMessageEncapsulation;
import network.core.messages.format.Request;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class ServerConnectionController extends AConnectionController<IServerExecuteHandler> {	
			
	private NetworkContainer<NetworkState> states;
	private NetworkContainer<Connection> clients;
	private ServerMessageHandler handler;
	
	public ServerConnectionController() {		
		states = new NetworkContainer<NetworkState>();
		clients = new NetworkContainer<Connection>();
		handler = new ServerMessageHandler();
	}
	
	/**
	 * Method used to add multiple connections to the network container. Will run the servers handshake with the given 
	 * Socket before adding it to the container. 
	 * 
	 * @param connection Creates a new connection given a Socket
	 */
	public void addConnection(Socket connection) {
		handshake(connection);
	}

	@Override
	protected void handshake(Socket connection) {
		// TODO better handshake
		System.out.println("Hub just connected to: " + connection.getLocalAddress());

		try {
			String newId = ServerIdManager.getNewClientId();
			Connection toAdd = new Connection(newId, incomingMessages, connection);
			toAdd.send(Request.CONNECTION, newId, null);
			clients.addObject(toAdd);
			
		} catch (IOException e) {
			System.out.println("Error when creating the client: " + e);
		}
	}
	
	@Override
	protected void handleMessage(IDMessageEncapsulation message) {
		// TODO Pre-Processing
		handler.getHandler(message.getMessage().getRequest()).executeMessage(message, clients, states);	
	}

	@Override
	protected void closeStream() {
		try {
			states.close();
			clients.close();
		} catch (Exception e) {
			System.out.println("Error closing connection: " + e);
		}
	}

	@Override
	protected void resetStream() throws IOException {
		//TODO
	}
}
