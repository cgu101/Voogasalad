package network.deprecated;

import java.io.Serializable;

import authoring.model.game.Game;
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;

/**
 * 
 * @author Austin
 */

public class AuthoringMessage implements Serializable {
	
	/**
	 *	Generated Serial ID 
	 */
	private static final long serialVersionUID = 8898809091241353334L;

	private static String GAME_NAME = PropertyKeyResource.getKey(PropertyKey.GAME_ID_KEY);
	
	private RequestType request;
	private String data;
	private Game game; 
	
	public AuthoringMessage (RequestType request, String data, Game gameState) {
		this.request = request;
		this.data = data;
		this.game = gameState;
	}
	
	public String getRequest () {
		return request.toString();
	}

	public String getData () {
		return data;
	}
	
	public String getGameName () { /* TODO: Remove this. For testing only */
		return (String) game.getProperty(GAME_NAME).getValue();
	}
}
