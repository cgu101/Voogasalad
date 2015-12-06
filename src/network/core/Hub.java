package network.core;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.connections.ConnectionToClient;
import network.core.connections.MessageHandlerThread;
import network.game.Message;


/**
 * A Hub is a server for a "netgame".  When a Hub is created, it will
 * listen for connection requests from clients on a specified port.
 * The clients are the players in the game.  Each player is identified
 * by an ID number that is assigned by the hub when the client connects.
 * Clients are defined by subclasses of the class netgame.commen.Client.
 * <p>A Hub is a "message center" that can send and receive messages.
 * A message can be any non-null object that implements the Serializable interface.
 * Many standard classes, including String, do this.  (So, a message might
 * simply be a string.)  When a message  is received, the protected method
 * messageReceived(sender,message) is called.  In this class, this method
 * simply wraps the message in a ForwardedMessage, which it then
 * sends to all connected clients.  That is, the Hub acts as a passive
 * forwarder of messages.  Subclasses will usually override this method
 * and will generally add other functionality to the Hub as well.
 * <p>The sendToAll(msg) method sends a message to all connected clients.
 * The sendToOne(playerID,msg) method will send the message to just the
 * client with the specified ID number.  If the same object is transmitted
 * more than once, it might be necessary to use the resetOutput() or
 * setAutoReset(true) methods.  See those methods for details.
 * <p>(Certain messages that are defined by package private classes in
 * the package netgame.common, are for internal use only.  These messages
 * do not result in a call to messageReceived, and they are not seen
 * by clients.)
 * 
 * <p>The communication protocol that is used internally goes as follows:
 *  <ul>
 *  <li>When the server receives a connection request, it expects to
 *  read a string from the client.  The string is "Hello Hub".</li>
 *  <li>The server responds by sending an object of type Integer 
 *  representing the unique ID number that has been assigned to the client.
 *  Clients are assigned the IDs 1, 2, 3, ..., in the order they connect.</li>
 *  <li>The extraHandshake() method is called.  This method does nothing
 *  in this class, but subclasses of Hub can override to do extra setup
 *  or checking before the connection is considered to be created.
 *  Note that if extraHandshake() throws an error, then the client is
 *  never considered connected, but that client's ID will not be reused.</li>
 *  <li>All connected clients, including the one that has just connected,
 *  are notified of the new client.  (The playerConnected() method in
 *  the client will be called.)</li>
 *  <li>Once a client has successfully connected, the client can send messages to
 *  the server.  Messages received from a client are passed to the
 *  messageReceived() method.</li>
 *  <li>If the client's disconnect() method is called, the hub is notified,
 *  and it in turn notifies all connected clients, not including the one
 *  that just disconnected.  (The clients' playerDisconnected() method
 *  is called.)</li>
 *  <li>If the hub's shutDownHub() method is called, all the clients
 *  will be notified, and the ServerSocket, if any still exists, is closed down.  
 *  One second later, any connection that has not closed normally is closed.
 *  </ul>
 */
public class Hub {

	/**
	 * A queue of messages received from clients.  When a method is received,
	 * it is placed in this queue.  A separate thread takes messages from the
	 * queue and processes them (in the order in which they were received).
	 */
	private LinkedBlockingQueue<Message> incomingMessages;
	private ServerSocket serverSocket;  
	private Thread serverThread;
	private Thread messageHandlerThread;
	volatile private boolean shutdown;
	private int nextClientId = 0;  
	private int port;

	/**
	 * Creates a Hub listening on a specified port, and starts a thread for
	 * processing messages that are received from clients.
	 * @param port  the port on which the server will listen.
	 * @throws IOException if it is not possible to create a listening socket on the specified port.
	 */
	public Hub(Integer port) throws IOException {
		this.port = port;
		incomingMessages = new LinkedBlockingQueue<Message>();
		serverSocket = new ServerSocket(port);
		System.out.println("Listening for client connections on port " + port);
		messageHandlerThread = new MessageHandlerThread(incomingMessages);
		serverThread = new ServerThread();
		messageHandlerThread.start();
		serverThread.start();
	}

	/**
	 * Stops listening, without disconnecting any currently connected clients.
	 * You might do this, for example, if some maximum number of player connections has
	 * been reached, as when a game only allows two players.
	 */
	public void shutdownServerSocket() {
		if (serverThread != null) {
			incomingMessages.clear();
			shutdown = true;
			try {
				serverSocket.close();
			} catch (IOException e) {}
			serverThread = null;
			serverSocket = null;
		}
	}


	/**
	 *  Disconnects all currently connected clients and stops accepting new client
	 *  requests.  It is still possible to restart listening after this method has
	 *  been called, by calling the restartServer() method.
	 */
	public void shutDownHub() {
		shutdownServerSocket();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		// TODO Need to set a method for killing all connections and saving all states
	}
	
	/**
	 * Restarts listening and accepting new clients.  This would only be used if
	 * the shutDownHub() method has been called previously.
	 * @param port the port on which the server should listen.
	 * @throws IOException if it is impossible to create a listening socket on the specified port.
	 */
	public void restartServer(int port) throws IOException {
		if (serverThread != null && serverThread.isAlive())
			throw new IllegalStateException("Server is already listening for connections.");
		shutdown = false;
		serverSocket = new ServerSocket(port);
		serverThread = new ServerThread();
		serverThread.start();
	}


	private class ServerThread extends Thread {
		public void run() {
			try {
				while (!shutdown) {
					ConnectionToClient toAdd = new ConnectionToClient(nextClientId++, incomingMessages, serverSocket.accept());
					((MessageHandlerThread) messageHandlerThread).addConnectionToClient(toAdd);
				}
			} catch (Exception e) {
				if (shutdown) {
					System.out.println("Listener socket has shut down.");
				} else {
					System.out.println("Listener socket has been shut down by error: " + e);
					try {
						restartServer(port);
					} catch (IOException e1) {}
				}
			}
		}
	}
}