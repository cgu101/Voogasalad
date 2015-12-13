package network.core.controller;

import network.core.connections.ClientConnection;
import network.core.containers.NetworkContainer;
import network.core.messages.Message;
import network.framework.format.Request;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class ServerErrorController {

	public static void sendErrorMessage(String client, NetworkContainer<ClientConnection> clients, String error) {
		clients.getObject(client).send(new Message(error, Request.ERROR, null));
	}
	
	public static void sendErrorMessage(ClientConnection client, String error) {
		client.send(new Message(error, Request.ERROR, null));
	}
}
