package network.core.controller.server;

import network.core.connections.Connection;
import network.core.connections.NetworkState;
import network.core.containers.NetworkContainer;
import network.core.messages.IDMessageEncapsulation;

public interface IServerExecuteHandler {
	void executeMessage(IDMessageEncapsulation message, NetworkContainer<Connection> clients, NetworkContainer<NetworkState> states);		
}
