package network.core.controller.server;

import java.util.HashMap;
import java.util.Map;

import network.core.connections.NetworkState;
import network.core.controller.server.MessageHandler.ExecuteHandler;
import network.core.messages.Message;
import network.core.messages.format.Request;

public class ClientMessageHandler  {
	
	private Map<Request, ExecuteHandler> myExecuters;
	
	public ClientMessageHandler() {
		myExecuters = new HashMap<Request, ExecuteHandler>();
		init();
	}
	
	
	private void init() {

		myExecuters.put(Request.ERROR, (message, clients, games)-> {
			System.out.println(Request.ERROR.toString());
			System.out.println(message.getMessage().getPaylad());
		});

		myExecuters.put(Request.CONNECTION, (message, clients, games)-> {
			System.out.println(Request.CONNECTION.toString());
		});

		myExecuters.put(Request.LOADGROUP, (message, clients, games)-> {
			System.out.println(Request.LOADGROUP.toString());
			Message msg = message.getMessage();
			
			
		});

		myExecuters.put(Request.CREATEGROUP, (message, clients, games)-> {
			System.out.println(Request.CREATEGROUP.toString());
		});
	}
}
