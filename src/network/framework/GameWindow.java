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

	private static final String HOST = "52.20.247.225";
	//private static final String HOST = "localhost";
	private static final int PORT = 5055;
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

			//addToTranscript("I HAVE RECEIVED! Sender ID is: " + msg.senderID + " and says:  " + msg.message.getClass());

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						updateObservers((Mail) message);
					}
				});

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
