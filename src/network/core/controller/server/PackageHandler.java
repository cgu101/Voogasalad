package network.core.controller.server;

import java.util.HashMap;
import java.util.Map;

import network.core.connections.Connection;
import network.core.connections.NetworkState;
import network.core.containers.NetworkContainer;
import network.core.messages.IDMessageEncapsulation;
import network.core.messages.format.Request;

public abstract class PackageHandler<T extends IMessageHandler> {
	protected Map<Request, ExecuteHandler> myExecuters;
	
	public PackageHandler() {
		myExecuters = new HashMap<Request, ExecuteHandler>();
		init();
	}
	
	public ExecuteHandler getHandler(Request type) {
		return myExecuters.get(type);
	}
	
	private void init() {

		myExecuters.put(Request.ERROR, (message, clients, games)-> {
			System.out.println(Request.ERROR.toString());
		});
		
		myExecuters.put(Request.CONNECTION, (message, clients, games)-> {
			System.out.println(Request.CONNECTION.toString());
		});
		
		myExecuters.put(Request.LOADGROUP, (message, clients, games)-> {
			System.out.println(Request.LOADGROUP.toString());
		});
		
		myExecuters.put(Request.CREATEGROUP, (message, clients, games)-> {
			System.out.println(Request.CREATEGROUP.toString());
		});
	}
	
	interface ExecuteHandler {
		void executeMessage(IDMessageEncapsulation message, NetworkContainer<Connection> clients, NetworkContainer<NetworkState> games);		
	}
}
