package network.deprecated;

import java.io.IOException;
import java.util.Observable;

import authoring.model.game.Game;
import javafx.application.Platform;
import network.core.client.Client;

/**
 * A currently deprecated class. Please see ClientAdapter
 * @author Austin
 *
 */
public class GameWindow extends Observable implements Proxy {

	private static final String HOST = "52.20.247.225";
	//private static final String HOST = "localhost";
	private static final int PORT = 5055;
	private GameClient connection; 
	private volatile boolean connected;
	private Game gameData; 
	
	private static final GameWindow window = new GameWindow();

	@Deprecated
	public static GameWindow getInstance() {
		return window;
	}

	@Deprecated
	private GameWindow() {

		new Thread() {
			public void run() {
				try {
					addToTranscript("Connecting to " + HOST + " ...");
					connection = new GameClient(HOST);
					connected = true;
					connection.send("I have connected to " + HOST);
				}
				catch (IOException e) {
					addToTranscript("Connection attempt failed.");
					addToTranscript("Error: " + e);
				}
			}
		}.start();

		gameData = new Game();
	}

	@Deprecated
	public Game requestServerObject () {
		return gameData;
	}

	@Deprecated
	private void addToTranscript (String message) {
		System.out.println(message);
	}

	@Deprecated
	public void updateObservers (Object o) {
		setChanged();
		notifyObservers(o);
	}
	
	@Deprecated
	@Override
	public void send(Object o) {
		send((Mail) o);
	}

	@Deprecated
	public void send (Mail message) {
		if (connection != null) {
			connection.send(message);
		}
	}

	@Deprecated
	public boolean isConnected () {
		return connected;
	}

	@Deprecated
	private class GameClient extends Client {

		@Deprecated
		GameClient(String host) throws IOException {
			super(host, PORT);
		}

		@Deprecated
		protected void messageReceived(Object message) {

			//addToTranscript("I HAVE RECEIVED! Sender ID is: " + msg.senderID + " and says:  " + msg.message.getClass());

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						updateObservers((Mail) message);
					}
				});

		}

		@Deprecated
		private boolean isMessageValid (ForwardedMessage m) {
			return (m.message instanceof Mail && m.senderID != this.getID());
		}
	
		@Deprecated
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
