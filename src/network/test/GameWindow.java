package network.test;

import java.io.IOException;
import java.util.Observable;

import authoring.model.game.Game;
import network.core.Client;
import network.core.ForwardedMessage;

public class GameWindow extends Observable {
	private final static int PORT = 6969;

	private class GameClient extends Client {

		GameClient(String host) throws IOException {
			super(host, PORT);
		}

		protected void messageReceived(Object message) {
			if (message instanceof ForwardedMessage) {
				ForwardedMessage bm = (ForwardedMessage)message;
				addToTranscript("I HAVE RECEIVED! Sender ID is: " + bm.senderID + " and says:  " + bm.message.getClass());
			}
		}

		protected void connectionClosedByError(String message) {
			addToTranscript("Sorry, communication has shut down due to an error:\n     " + message);
			connected = false;
			connection = null;
		}

		protected void playerConnected(int newPlayerID) {
			addToTranscript("Someone new has joined the authoring environment, with ID number " + newPlayerID);
		}

		protected void playerDisconnected(int departingPlayerID) {
			addToTranscript("The person with ID number " + departingPlayerID + " has left the authoring environment");
		}

	}

	private GameClient connection; 
	private volatile boolean connected;
	
	private Game gameData;

	public GameWindow(final String host) {

		new Thread() {
			public void run() {
				try {
					addToTranscript("Connecting to " + host + " ...");
					connection = new GameClient(host);
					connected = true;
					connection.send("I have connected to " + host);

					/**
					 * Instead of creating a new game as the game data, we want to try to obtain a Game object from the server side
					 * using a obtainFromServer() method --> gameData = obtainFromServer();
					 */
				}
				catch (IOException e) {
					addToTranscript("Connection attempt failed.");
					addToTranscript("Error: " + e);
				}
			}
		}.start();
		
		gameData = new Game();
	}



	private void addToTranscript (String message) {
		System.out.println(message);
	}
	
	public void updateObservers (Object o) {
		setChanged();
		notifyObservers(o);
	}
	
	public void send (Object message) {
		connection.send(message);
	}
	
	public Game getGameData () {
		return gameData;
	}
	
	public void setGameData (Game game) {
		gameData = game;
	}
}
