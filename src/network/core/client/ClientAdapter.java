// This entire file is part of my masterpiece.
// Austin Liu

package network.core.client;

import authoring.model.game.Game;
import network.core.controller.client.ClientConnectionController;
import network.core.messages.Message;
import network.core.messages.format.Request;

/**
 * @author Austin Liu (abl17) and Chris Streiffer (cds33)
 * Class that represents the client framework
 */

public class ClientAdapter {
	
	private ClientConnectionController myConnectionController;
	
	private static final ClientAdapter myAdapter = new ClientAdapter();
	
	private ClientAdapter () {
		myConnectionController = ClientConnectionController.getInstance();
	}
	
	/**
	 * @return Singleton of Client to be used no matter the data architecture
	 */
	public static ClientAdapter getInstance () {
		return myAdapter;
	}
	
	public static void main (String[] args) {
		Game g = new Game("GAME_ID_1");
		ClientConnectionController.getInstance().getConnection().send(new Message(g, Request.LOADGROUP, "eragreg"));
	}
	
	public ClientConnectionController getMyConnectionController() {
		return myConnectionController;
	}
}