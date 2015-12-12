package network.core.connections;

import java.util.ArrayList;
import java.util.List;

import authoring.model.bundles.Identifiable;
import authoring.model.game.Game;

public class NetworkObjectState implements Identifiable, ICloseable  {
	
	private static final Long DELAY = 2700000l;
	
	private String gameIdentifier;
	private Game game;
	private HeartbeatValue heartbeatVal;
	private Heartbeat heartbeat;
	private List<Integer> clients;
	
	public NetworkObjectState(String gameIdentifier, Game game, Integer client) {
		this.gameIdentifier = gameIdentifier;
		this.game = game;
		heartbeatVal = new HeartbeatValue();
		clients = new ArrayList<Integer>();
		clients.add(client);
		initializeHeartbeat();
	}
	
	public void addClient(Integer client) {
		clients.add(client);
	}
	
	public void removeClient(Integer client) {
		clients.remove(client);
	}
	
	public List<Integer> getClients() {
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

	@Override
	public Boolean isClosed() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
