package network.core.controller;

import java.util.HashMap;
import java.util.Map;

import network.core.connections.ClientConnection;
import network.core.connections.NetworkGameState;
import network.core.containers.NetworkContainer;
import network.core.messages.ServerMessage;
import network.deprecated.ForwardedMessage;
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

		myExecuters.put(Request.ERROR, (message, clients, games)-> {
			System.out.println("ERROR");
		});
		
		myExecuters.put(Request.CONNECTION, (message, clients, games)-> {
			System.out.println("CONNECTION");
		});
		
		myExecuters.put(Request.LOADGROUP, (message, clients, games)-> {
			System.out.println("LOADGROUP");
		});
		
		myExecuters.put(Request.CREATEGROUP, (message, clients, games)-> {
			System.out.println("CREATEGROUP");
		});
	}
	
	private void forwardToAll(ServerMessage message, NetworkContainer<ClientConnection> clients) {
		
	}
	
	interface ExecuteHandler {	
		void executeMessage(ServerMessage message, NetworkContainer<ClientConnection> clients, NetworkContainer<NetworkGameState> games);		
	}
}
