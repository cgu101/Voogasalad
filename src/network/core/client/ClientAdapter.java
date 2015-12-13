package network.core.client;

import java.io.IOException;

import network.util.ThreadFactory;
import network.core.controller.client.ClientConnectionController;
import network.util.ConnectionType;

/**
 * @author Austin Liu (abl17) and Chris Streiffer (cds33)
 * Class that represents the client framework
 */

public class ClientAdapter {
	
	private NetworkClient myClient;
	private volatile boolean isConnected;
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
	
	/**
	 * Private network class
	 * @author Chris & Austin
	 */

	private class NetworkClient extends Client {

		private static final int DEFAULT_PORT = 5959;
		
		NetworkClient(String host) throws IOException {
			super(host, DEFAULT_PORT);
		}

		protected void messageReceived(Object message) {
			Runnable myRunnable =  new Runnable() { public void run() {}};
			ThreadFactory.execute(myRunnable, ConnectionType.JAVAFX);
		}
	
	}
}
