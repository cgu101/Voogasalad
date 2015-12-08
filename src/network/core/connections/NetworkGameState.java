package network.core.connections;

import java.util.ArrayList;
import java.util.List;

import authoring.model.bundles.Identifiable;
import network.deprecated.GameState;

public class NetworkGameState implements Identifiable, Closeable  {
	
	private static final Long DELAY = 2700000l;
	
	private String gameIdentifier;
	private GameState state;
	private HeartbeatValue heartbeatVal;
	private Heartbeat heartbeat;
	private List<String> clientIds;
	
	public NetworkGameState(String gameIdentifier, GameState state, String client) {
		this.gameIdentifier = gameIdentifier;
		this.state = state;
		heartbeatVal = new HeartbeatValue();
		clientIds = new ArrayList<String>();
		clientIds.add(client);
		initializeHeartbeat();
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
