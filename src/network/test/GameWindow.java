package network.test;

import java.io.IOException;

import network.core.Client;
import network.core.ForwardedMessage;
import view.screen.CreatorScreen;

public class GameWindow {
	private final static int PORT = 6969;

	public class GameClient extends Client {

		GameClient(String host) throws IOException {
			super(host, PORT);
		}

		protected void messageReceived(Object message) {
			if (message instanceof ForwardedMessage) {  // (no other message types are expected)
				ForwardedMessage bm = (ForwardedMessage)message;
				addToTranscript("I HAVE RECEIVED! Sender ID is: " + bm.senderID + " and says:  " + bm.message);
			}
		}

		protected void connectionClosedByError(String message) {
			addToTranscript("Sorry, communication has shut down due to an error:\n     " + message);
			connected = false;
			connection = null;
		}

		protected void playerConnected(int newPlayerID) {
			addToTranscript("Someone new has joined the chat room, with ID number " + newPlayerID);
		}

		protected void playerDisconnected(int departingPlayerID) {
			addToTranscript("The person with ID number " + departingPlayerID + " has left the chat room");
		}

	}

	private GameClient connection; 
	private CreatorScreen myCreatorScreen;
	private volatile boolean connected; 

	public GameWindow(final String host) {
		myCreatorScreen = new CreatorScreen();
		myCreatorScreen.setGameWindow(this);


		new Thread() {
			public void run() {
				try {
					addToTranscript("Connecting to " + host + " ...");
					connection = new GameClient(host);
					connected = true;
					connection.send("yayyyyyyyyyyyy");
				}
				catch (IOException e) {
					addToTranscript("Connection attempt failed.");
					addToTranscript("Error: " + e);
				}
			}
		}.start();
	}



	public void addToTranscript (String message) {
		System.out.println(message);
	}

	public GameClient getClient () {
		return connection;
	}

	public CreatorScreen getScreen () {
		return myCreatorScreen;
	}
}
