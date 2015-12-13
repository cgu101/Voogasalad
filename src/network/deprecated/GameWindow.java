package network.deprecated;

import java.io.IOException;
import java.util.Observable;

import authoring.model.game.Game;
import javafx.application.Platform;

/**
 * A currently deprecated class. Please see ClientAdapter
 * @author Austin
 *
 */
public class GameWindow extends Observable implements Proxy {

	private static final String HOST = "52.20.247.225";
	//private static final String HOST = "localhost";
	private static final int PORT = 5055;
	private volatile boolean connected;
	private Game gameData; 
	
	private static final GameWindow window = new GameWindow();

	@Deprecated
	public static GameWindow getInstance() {
		return window;
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
	public boolean isConnected () {
		return connected;
	}
}
