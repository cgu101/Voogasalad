package network.core.containers;

import java.util.HashMap;
import java.util.Map;

import network.core.connections.GameThread;

public class GameThreadContainer {

	private Map<String, GameThread> gameMap;
	
	public GameThreadContainer() {
		gameMap = new HashMap<String, GameThread>();
	}
	
}
