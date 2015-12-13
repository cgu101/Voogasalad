package network.core.controller.server;

import java.util.HashMap;
import java.util.Map;

import network.core.connections.Connection;
import network.core.connections.NetworktState;
import network.core.containers.NetworkContainer;
import network.core.messages.IDMessage;
import network.core.messages.format.Request;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class MessageHandler {
	
	private Map<Request, ExecuteHandler> myExecuters;
	
	public MessageHandler() {
		myExecuters = new HashMap<Request, ExecuteHandler>();
		init();
	}
	
	public ExecuteHandler getHandler(Request type) {
		return myExecuters.get(type);
	}
	
	private void init() {

		myExecuters.put(Request.ERROR, (message, clients, games)-> {
			System.out.println("ERROR");
		});
		
		myExecuters.put(Request.CONNECTION, (message, clients, games)-> {
			System.out.println("CONNECTION");
		});
		
		myExecuters.put(Request.LOADGROUP, (message, clients, games)-> {
			System.out.println("LOADGROUP");
		});
		
		myExecuters.put(Request.CREATEGROUP, (message, clients, games)-> {
			System.out.println("CREATEGROUP");
		});
	}
	
//	private void forwardToAll(ServerMessage message, NetworkContainer<Connection> clients) {
//		
//	}
	
	interface ExecuteHandler {	
		void executeMessage(IDMessage message, NetworkContainer<Connection> clients, NetworkContainer<NetworktState> games);		
	}
}
