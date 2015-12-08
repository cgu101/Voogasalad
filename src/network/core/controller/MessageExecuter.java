package network.core.controller;

import java.util.HashMap;
import java.util.Map;

import network.core.Message;
import network.core.containers.ClientConnectionContainer;
import network.core.containers.NetworkGameStateContainer;
import network.framework.format.Request;

public class MessageExecuter {
	
	private Map<Request, ExecuteHandler> myExecuters;
	
	public MessageExecuter() {
		myExecuters = new HashMap<Request, ExecuteHandler>();
		init();
	}
	
	public ExecuteHandler getHandler(Request type) {
		return myExecuters.get(type);
	}
	
	private void init() {
		// TODO Handle add request
		myExecuters.put(Request.ADD, null);
		
		// TODO Handle delete request
		myExecuters.put(Request.DELETE, null);
		
		// TODO Handle modify request
		myExecuters.put(Request.MODIFY, null);
		
		// TODO Handle transition request
		myExecuters.put(Request.TRANSITION, null);
	}
	
	private interface ExecuteHandler {	
		public void executeMessage(Message message, ClientConnectionContainer clients, NetworkGameStateContainer games);		
	}

}
