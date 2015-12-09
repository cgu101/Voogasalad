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
import network.framework.format.Mail;
import network.framework.format.Request;

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
		myExecuters.put(Request.NODE, (message, clients, games)-> {
			System.out.println("TREE NODE");
			forwardToAll(message, clients);
		});
		
		myExecuters.put(Request.GAME, (message, clients, games)-> {
			Game g = (Game) ((Mail) message.message).getData();
			if(!games.getKeys().contains(g.getUniqueID())) {
				games.addObject(new NetworkGameState(g.getUniqueID(), g, message.senderID));
				System.out.println("Did something");
			}
		});
		
		myExecuters.put(Request.DISCONNECT, (message, clients, games)-> {
			forwardToAll(message, clients);
//			System.out.println("DISCONNECT");
		});
		
		myExecuters.put(Request.ADD, (message, clients, games)-> {
			forwardToAll(message, clients);
		});
//		
//		// TODO Handle delete request
		myExecuters.put(Request.DELETE, (message, clients, games)-> {
			forwardToAll(message, clients);
			System.out.println("DELETE");
		});
		
		myExecuters.put(Request.MODIFY, (message, clients, games)-> {
			forwardToAll(message, clients);
			System.out.println("MODIFY");
		});

//		// TODO Handle transition request
		myExecuters.put(Request.TRANSITION, (message, clients, games)-> {
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
