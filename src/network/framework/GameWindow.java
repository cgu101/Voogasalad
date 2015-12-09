package network.framework;

import java.io.IOException;
import java.util.Observable;

import authoring.model.game.Game;
import javafx.application.Platform;
import network.core.Client;
import network.core.ForwardedMessage;
import network.framework.format.Mail;
import network.framework.format.Proxy;

public class GameWindow extends Observable implements Proxy {

	private static final String HOST = "localhost";
	private static final int PORT = 6969;
	private GameClient connection; 
	private volatile boolean connected;
	private Game gameData; 
	private static final GameWindow window = new GameWindow();

	public static GameWindow getInstance() {
		return window;
	}

	private GameWindow() {

		new Thread() {
			public void run() {
				try {
					addToTranscript("Connecting to " + HOST + " ...");
					connection = new GameClient(HOST);
					connected = true;
					connection.send("I have connected to " + HOST);

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

	public Game requestServerObject () {
		return gameData;
	}

	private void addToTranscript (String message) {
		System.out.println(message);
	}

	public void updateObservers (Object o) {
		setChanged();
		notifyObservers(o);
	}
	
	@Override
	public void send(Object o) {
		send((Mail) o);
	}

	public void send (Mail message) {
		if (connection != null) {
			connection.send(message);
		}
	}

	public boolean isConnected () {
		return connected;
	}

	private class GameClient extends Client {

		GameClient(String host) throws IOException {
			super(host, PORT);
		}

		protected void messageReceived(Object message) {
			ForwardedMessage msg = (ForwardedMessage) message;
			addToTranscript("I HAVE RECEIVED! Sender ID is: " + msg.senderID + " and says:  " + msg.message.getClass());
			if (isMessageValid(msg)) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						updateObservers((Mail) msg.message);
					}
				});
			}
		}

		private boolean isMessageValid (ForwardedMessage m) {
			return (m.message instanceof Mail && m.senderID != this.getID());
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
}
