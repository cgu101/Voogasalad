package network.core.connections;

import java.util.ArrayList;
import java.util.List;

import authoring.model.bundles.Identifiable;
import authoring.model.game.Game;

public class NetworkGameState implements Identifiable, Closeable  {
	
	private static final Long DELAY = 2700000l;
	
	private String gameIdentifier;
	private Game game;
	private HeartbeatValue heartbeatVal;
	private Heartbeat heartbeat;
	private List<String> clients;
	
	public NetworkGameState(String gameIdentifier, Game game, String client) {
		this.gameIdentifier = gameIdentifier;
		this.game = game;
		heartbeatVal = new HeartbeatValue();
		clients = new ArrayList<String>();
		clients.add(client);
		initializeHeartbeat();
	}
	
	public void addClient(String client) {
		clients.add(client);
	}
	
	public void removeClient(String client) {
		clients.remove(client);
	}
	
	public List<String> getClients() {
		return clients;
	}
	
	public Game getGame() {
		return game;
	}

	@Override
	public String getUniqueID() {
		return gameIdentifier;
	}

	@Override
	public Identifiable getCopy() {
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}
	
	private void initializeHeartbeat() {
		heartbeat = new Heartbeat(DELAY) {

			@Override
			public void heartbeat() {
				// TODO Auto-generated method stub
			}			
		};
	}
	
	public HeartbeatValue getHeartbeatValue() {
		return heartbeatVal;
	}
		
}
