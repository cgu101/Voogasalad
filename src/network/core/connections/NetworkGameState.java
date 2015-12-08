package network.core.connections;

import java.util.ArrayList;
import java.util.List;

import authoring.model.bundles.Identifiable;
import network.deprecated.GameState;

public class NetworkGameState implements Identifiable, Heartbeat {
	
	private String gameIdentifier;
	private GameState state;
	private HeartbeatValue heartbeat;
	private List<String> clientIds;
	
	public NetworkGameState(String gameIdentifier, GameState state, String client) {
		this.gameIdentifier = gameIdentifier;
		this.state = state;
		heartbeat = new HeartbeatValue();
		clientIds = new ArrayList<String>();
		clientIds.add(client);
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
	public void heartbeat() {
		heartbeat.update();		
	}
		
}
