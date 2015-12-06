package network.core.containers;

import java.util.HashMap;
import java.util.Map;

import network.core.connections.ConnectionToClient;

public class ClientConnectionContainer {
	
	private Map<String, ConnectionToClient> clientMap;
	
	public ClientConnectionContainer() {
		clientMap = new HashMap<String, ConnectionToClient>();
	}
	
}
