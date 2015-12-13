package network.core.controller.client;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

import network.core.connections.Connection;
import network.core.messages.IDMessageEncapsulation;
import network.core.messages.Message;

public interface IClientExecuteHandler {

	void execute(IDMessageEncapsulation message, Connection clientConnection, Map<String, BlockingQueue<Message>> myQueues);
}
