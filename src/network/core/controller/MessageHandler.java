package network.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.game.Game;
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
		myExecuters.put(Request.DISCONNECT, (message, clients, games)-> {
			String clientId = message.getClient();
			String gameId = clients.getObject(clientId).getGameId();
			
			games.getObject(gameId).removeClient(clientId);
			clients.getObject(clientId).close();
			clients.removeObject(clientId);
			System.out.println("DISCONNECT");
		});
		
		myExecuters.put(Request.ADD, (message, clients, games)-> {
			Game data = (Game) message.getMail().getData();
			String gameId = data.getUniqueID();
			String clientId = message.getClient();

			NetworkGameState game = null;
			if((game = games.getObject(gameId)) != null) {
				game.addClient(gameId);
			} else {
				games.addObject(new NetworkGameState(gameId, data, clientId));
			}
			clients.getObject(clientId).setGameId(gameId);
			System.out.println("ADD");
		});
		
		// TODO Handle delete request
		myExecuters.put(Request.DELETE, (message, clients, games)-> {
			System.out.println("DELETE");
		});
		
		myExecuters.put(Request.MODIFY, (message, clients, games)-> {
			// Extract all the info you need
			String clientId = message.getClient();
			String gameId = clients.getObject(clientId).getGameId();
			NetworkGameState gameState = games.getObject(gameId);
			Mail changes = message.getMail();
			
			// TODO Update the game data
			
			
			// Send update to clients
			List<String> clientIds = gameState.getClients();
			for(String s : clientIds) {
				clients.getObject(s).send(changes);
			}			
			System.out.println("MODIFY");
		});
		
		// TODO Handle transition request
		myExecuters.put(Request.TRANSITION, (message, clients, games)-> {
			System.out.println("TRANSITION");
		});
	}
	
	interface ExecuteHandler {	
		void executeMessage(Message message, NetworkContainer<ClientConnection> clients, NetworkContainer<NetworkGameState> games);		
	}
}
