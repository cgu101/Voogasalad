package network.core.connections;

import java.util.List;

import authoring.model.bundles.Identifiable;
import network.deprecated.GameState;

public class NetworkGameState implements Identifiable {
	
	private String identifier;
	private Heartbeat heartbeat;
	private GameState myGame;
	private List<String> clientIds;
	
	public NetworkGameState(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String getUniqueID() {
		return identifier;
	}

	@Override
	public Identifiable getCopy() {
		return null;
	}
		
}
