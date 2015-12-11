package network.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.game.Game;
import network.core.ForwardedMessage;
import network.core.Message;
import network.core.connections.ClientConnection;
import network.core.connections.NetworkGameState;
import network.core.containers.NetworkContainer;
import network.deprecated.RequestType;
import network.framework.format.Mail;

public class MessageHandler {
	
	private Map<RequestType, ExecuteHandler> myExecuters;
	
	public MessageHandler() {
		myExecuters = new HashMap<RequestType, ExecuteHandler>();
		init();
	}
	
	public ExecuteHandler getHandler(RequestType type) {
		return myExecuters.get(type);
	}
	
	private void init() {
		myExecuters.put(RequestType.NODE, (message, clients, games)-> {
			System.out.println("TREE NODE");
			forwardToAll(message, clients);
		});
		
		myExecuters.put(RequestType.GAME, (message, clients, games)-> {
			Game g = (Game) ((Mail) message.message).getData();
			if(!games.getKeys().contains(g.getUniqueID())) {
				games.addObject(new NetworkGameState(g.getUniqueID(), g, message.senderID));
				System.out.println("Did something");
			}
		});
		
		myExecuters.put(RequestType.DISCONNECT, (message, clients, games)-> {
			forwardToAll(message, clients);
//			System.out.println("DISCONNECT");
		});
		
		myExecuters.put(RequestType.ADD, (message, clients, games)-> {
			forwardToAll(message, clients);
		});
//		
//		// TODO Handle delete request
		myExecuters.put(RequestType.DELETE, (message, clients, games)-> {
			forwardToAll(message, clients);
			System.out.println("DELETE");
		});
		
		myExecuters.put(RequestType.MODIFY, (message, clients, games)-> {
			forwardToAll(message, clients);
			System.out.println("MODIFY");
		});

//		// TODO Handle transition request
		myExecuters.put(RequestType.TRANSITION, (message, clients, games)-> {
			forwardToAll(message, clients);
			System.out.println("TRANSITION");
		});
	}
	
	private void forwardToAll(ForwardedMessage message, NetworkContainer<ClientConnection> clients) {
		String sender = Integer.toString(message.senderID);
		for(String s : clients.getKeys()) {
			if(!s.equals(sender)) {
				clients.getObject(s).send(message.message);
			}
		}
	}
	
	interface ExecuteHandler {	
		void executeMessage(ForwardedMessage message, NetworkContainer<ClientConnection> clients, NetworkContainer<NetworkGameState> games);		
	}
}
