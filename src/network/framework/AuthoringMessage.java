package network.framework;

import java.io.Serializable;

/**
 * 
 * @author Austin
 */

public class AuthoringMessage implements Serializable {
	private Request request;
	private String data;
	private SGameState gameState; 
	
	public AuthoringMessage (Request request, String data, SGameState gameState) {
		this.request = request;
		this.data = data;
		this.gameState = gameState;
	}
	
	public String getRequest () {
		return request.toString();
	}

	public String getData () {
		return data;
	}
	
	public String getGameName () { /* TODO: Remove this. For testing only */
		return gameState.getName();
	}
}
