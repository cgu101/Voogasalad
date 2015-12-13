package network.core.controller.server;

import java.util.HashMap;
import java.util.Map;

import network.core.connections.Connection;
import network.core.connections.NetworkState;
import network.core.containers.NetworkContainer;
import network.core.messages.IDMessageEncapsulation;
import network.core.messages.Message;
import network.core.messages.format.Request;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Handles the messages that are inserted 
 */

public class MessageHandler {
	
	private Map<Request, ExecuteHandler> myExecuters;
	
	public MessageHandler() {
		myExecuters = new HashMap<Request, ExecuteHandler>();
		init();
	}
	
	public ExecuteHandler getHandler(Request type) {
		return myExecuters.get(type);
	}
	
	private void init() {

		myExecuters.put(Request.ERROR, (message, clients, games)-> {
			System.out.println(Request.ERROR.toString());
		});
		
		myExecuters.put(Request.CONNECTION, (message, clients, games)-> {
			System.out.println(Request.CONNECTION.toString());
		});
		
		myExecuters.put(Request.LOADGROUP, (message, clients, games)-> {
			Connection client = clients.getObject(message.getClientId());
			Message msg = message.getMessage();
			
			NetworkState state;
			if((state = games.getObject(msg.getID())) != null) {				
				client.setStateId(state.getID());
				client.send(Request.LOADGROUP, state.getState(), msg.getID());
			} else {
				ErrorController.sendErrorMessage(client, "No State found by id: " + msg.getID());
			}			
		});
		
		myExecuters.put(Request.CREATEGROUP, (message, clients, games)-> {
			String clientId = message.getClientId();
			Connection client = clients.getObject(clientId);
			Message msg = message.getMessage();
			
			if(!games.containsObject(msg.getID())) {
				clients.getObject(clientId).setStateId(msg.getID());
				NetworkState toAdd = new NetworkState(msg.getID(), msg.getPaylad(), clientId);
				games.addObject(toAdd);
			} else {
				ErrorController.sendErrorMessage(client, "State already exists with given id: " + msg.getID());
			}

			System.out.println(Request.CREATEGROUP.toString());
		});
		
		myExecuters.put(Request.GENERALDATA, (message, clients, games)-> {
			System.out.println(Request.CREATEGROUP.toString());
		});
			
		myExecuters.put(Request.QUEUEDATA, (message, clients, games)-> {
			System.out.println(Request.CREATEGROUP.toString());
		});
		
	}
	
	interface ExecuteHandler {	
		void executeMessage(IDMessageEncapsulation message, NetworkContainer<Connection> clients, NetworkContainer<NetworkState> games);		
	}
}
