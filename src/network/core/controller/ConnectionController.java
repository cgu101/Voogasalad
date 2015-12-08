package network.core.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.Message;
import network.core.connections.ClientConnection;
import network.core.connections.NetworkGameState;
import network.core.connections.threads.ConnectionThread;
import network.core.containers.ANetworkContainer;
import network.core.containers.ClientConnectionContainer;
import network.core.containers.NetworkGameStateContainer;

public class ConnectionController extends ConnectionThread {	
	
	private static final Long HEARTBEAT_DELAY = 1800000l;
	
	private ANetworkContainer<NetworkGameState> games;
	private ANetworkContainer<ClientConnection> clients;
	private BlockingQueue<Message> incomingMessages;
	private MessageHandler handler;
	private Timer heartbeat;
	
	public ConnectionController() {
		// Initialization stuff
		games = new NetworkGameStateContainer();
		clients = new ClientConnectionContainer();
		incomingMessages = new LinkedBlockingQueue<Message>();
		handler = new MessageHandler();
		initHeartbeatTimer();
	}
		
	@Override
	public void execute() {
		
		try {
			Message m = incomingMessages.take();
			handler.getHandler(m.getMail().getRequest()).executeMessage(m, clients, games);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		super.close();
		// Shutdown all of the threads and save the game states
	}
	
	public void addConnection(Socket connection) {
		// Initialize a new controller connection
		handshake(connection);
	}
	
	private void handshake(Socket connection) {
		// TODO the actual handshake
		
		// Then create the client
		try {
			clients.addObject(new ClientConnection(IdManager.getNewClientId(), incomingMessages, connection));
		} catch (IOException e) {
			System.out.println("Error when creating the client: " + e);
		}
	}
	
	private void initHeartbeatTimer() {
		heartbeat = new Timer();
		heartbeat.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		        games.heartbeat();
		        clients.heartbeat();
		    }

		}, 0, HEARTBEAT_DELAY);
	}
}
