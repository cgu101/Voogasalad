package network.core.controller.server;

import java.util.HashMap;
import java.util.List;
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

		myExecuters.put(Request.ERROR, (message, clients, states)-> {
			System.out.println(Request.ERROR.toString());
		});
		
		myExecuters.put(Request.CONNECTION, (message, clients, states)-> {
			System.out.println(Request.CONNECTION.toString());
		});
		
		myExecuters.put(Request.LOADGROUP, (message, clients, states)-> {
			Connection client = clients.getObject(message.getClientId());
			Message msg = message.getMessage();
			
			NetworkState state;
			if((state = states.getObject(msg.getID())) != null) {				
				client.setStateId(state.getID());
				client.send(Request.LOADGROUP, state.getState(), msg.getID());
			} else {
				ErrorController.sendErrorMessage(client, "No State found by id: " + msg.getID());
			}			
		});
		
		myExecuters.put(Request.CREATEGROUP, (message, clients, states)-> {
			String clientId = message.getClientId();
			Connection client = clients.getObject(clientId);
			Message msg = message.getMessage();
			
			if(!states.containsObject(msg.getID())) {
				clients.getObject(clientId).setStateId(msg.getID());
				NetworkState toAdd = new NetworkState(msg.getID(), msg.getPaylad(), clientId);
				states.addObject(toAdd);
			} else {
				ErrorController.sendErrorMessage(client, "State already exists with given id: " + msg.getID());
			}

			System.out.println(Request.CREATEGROUP.toString());
		});
		
		myExecuters.put(Request.GENERALDATA, (message, clients, states)-> {			
			forwardToAll(message, clients, states);
			System.out.println(Request.CREATEGROUP.toString());
		});
			
		myExecuters.put(Request.QUEUEDATA, (message, clients, states)-> {
			forwardToAll(message, clients, states);
			System.out.println(Request.CREATEGROUP.toString());
		});
	}
	
	private void forwardToAll(IDMessageEncapsulation message, NetworkContainer<Connection> clients, NetworkContainer<NetworkState> states) {
		String stateId = clients.getObject(message.getClientId()).getStateId();
		List<String> clientIds = states.getObject(stateId).getClients();
		
		for(String s : clientIds) {
			if(!s.equals(message.getClientId())) {
				clients.getObject(s).send(message.getMessage());
			}
		}
	}

	interface ExecuteHandler {	
		void executeMessage(IDMessageEncapsulation message, NetworkContainer<Connection> clients, NetworkContainer<NetworkState> states);		
	}
}
