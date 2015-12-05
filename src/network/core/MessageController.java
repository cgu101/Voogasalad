package network.core;

import java.util.HashMap;
import java.util.Map;

import network.core.Hub.Message;

public class MessageController {
	
	private Map<String, GameThread> currentGames;
	private Map<String, MessageHandler> messageHandlers;
	
	
	public MessageController() {
		currentGames = new HashMap<String, GameThread>();
		messageHandlers = new HashMap<String, MessageHandler>();
	}
	
	public Map<Integer, Message> handleMessage(Message msg) {
		// TODO parse message, extract enum, data, gamedata and do something
		
		return null;
	}
	
	private interface MessageHandler {		
		Map<String, Message> handleMessage(String data, Object gameData);
	}
	
	private void addLambdas() {
		// TODO add lambdas based on enums to this
	}

}
