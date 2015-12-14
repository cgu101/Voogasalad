// This entire file is part of my masterpiece.
// Christopher Streiffer
package network.core.controller.server;

import java.util.List;
import java.util.Map;

import network.core.connections.Connection;
import network.core.connections.NetworkState;
import network.core.containers.NetworkContainer;
import network.core.controller.AMessageHandler;
import network.core.controller.ErrorController;
import network.core.messages.IDMessageEncapsulation;
import network.core.messages.Message;
import network.core.messages.format.Request;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Handles the messages that are inserted 
 */

public class ServerMessageHandler extends AMessageHandler<IServerExecuteHandler> {
	
	
	@Override
	protected void init(Map<Request, IServerExecuteHandler> executers) {

		executers.put(Request.ERROR, (message, clients, states)-> {
			System.out.println(Request.ERROR.toString());
		});
		
		executers.put(Request.CONNECTION, (message, clients, states)-> {
			System.out.println(Request.CONNECTION.toString());
		});
		
		executers.put(Request.LOADGROUP, (message, clients, states)-> {
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
		
		executers.put(Request.CREATEGROUP, (message, clients, states)-> {
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
		
		executers.put(Request.GENERALDATA, (message, clients, states)-> {			
			forwardToAll(message, clients, states);
			System.out.println(Request.CREATEGROUP.toString());
		});
			
		executers.put(Request.QUEUEDATA, (message, clients, states)-> {
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
}
