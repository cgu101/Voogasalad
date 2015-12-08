package network.core.containers;

import network.core.connections.ClientConnection;

public class ClientConnectionContainer extends ANetworkContainer<ClientConnection> {

	@Override
	public void heartbeat() {
		// TODO Heartbeat to check connections
	}

}
