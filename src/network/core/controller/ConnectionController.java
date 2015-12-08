package network.core.controller;

import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.Message;
import network.core.connections.threads.ConnectionThread;
import network.core.containers.ClientConnectionContainer;
import network.core.containers.NetworkGameStateContainer;

public class ConnectionController extends ConnectionThread {	
	
	private static final Long HEARTBEAT_DELAY = 1800000l;
	
	private Timer heartbeat;
	private NetworkGameStateContainer games;
	private ClientConnectionContainer connections;
	private LinkedBlockingQueue<Message> incomingMessages;
	
	public ConnectionController() {
		// Initialization stuff
		initHeartbeatTimer();
	}
		
	@Override
	public void execute() {
		
		try {
			Message m = incomingMessages.take();
			
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
		// Performs handshake with the client to determine how to initialize it
		
	}
	
	private void initHeartbeatTimer() {
		heartbeat = new Timer();
		heartbeat.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		        games.heartbeat();
		        connections.heartbeat();
		    }

		}, 0, HEARTBEAT_DELAY);
	}
}
