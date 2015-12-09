package network.core.connections.threads;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import network.core.controller.ConnectionController;

public class Hub {

	private ServerSocket serverSocket;  
	private Thread serverThread;
	private Integer port;
	volatile private boolean shutdown;
	
	/**
	 * Creates a Hub listening on a specified port, and starts a thread for
	 * processing messages that are received from clients.
	 * @param port  the port on which the server will listen.
	 * @throws IOException if it is not possible to create a listening socket on the specified port.
	 */
	public Hub(Integer port) throws IOException {
		this.port = port;
		serverSocket = new ServerSocket(port);
		System.out.println("Listening for client connections on port " + port);
		serverThread = new ServerThread();
		serverThread.start();
	}

	/**
	 * Stops listening, without disconnecting any currently connected clients.
	 * You might do this, for example, if some maximum number of player connections has
	 * been reached, as when a game only allows two players.
	 */
	public void shutdownServerSocket() {
		if (serverThread != null) {
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
	}
	
	/**
	 * Restarts listening and accepting new clients.  This would only be used if
	 * the shutDownHub() method has been called previously.
	 * @param port the port on which the server should listen.
	 * @throws IOException if it is impossible to create a listening socket on the specified port.
	 */
	public void restartServer() throws IOException {
		if (serverThread != null && serverThread.isAlive())
			throw new IllegalStateException("Server is already listening for connections.");
		shutdown = false;
		serverSocket = new ServerSocket(port);
		serverThread = new ServerThread();
		serverThread.start();
	}

	private class ServerThread extends Thread {
		
		private ConnectionController controller;
		
		private ServerThread() {
			controller = new ConnectionController();
			controller.start();
		}
		
		public void run() {
			try {
				while (!shutdown) {
					Socket connection = serverSocket.accept();
					controller.addConnection(connection);
				}
			} catch (Exception e) {
				if (shutdown) {
					try {
						System.out.println("Listener socket has shut down.");
						controller.close();
						join();
					} catch (InterruptedException e1) {
						System.out.println("Error shutting down server thread: " + e);
					}
				} else {
					System.out.println("Listener socket has been shut down by error: " + e + "\nAttempting to restart server.");
					try {
						restartServer();
					} catch (IOException e1) {
						System.out.println("Restart has encountered an error, shutting down: " + e);
					}
				}
			}
		}
	}
}