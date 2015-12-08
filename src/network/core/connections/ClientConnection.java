package network.core.connections;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import authoring.model.bundles.Identifiable;
import network.core.Message;
import network.core.connections.threads.ConnectionThread;
import network.core.connections.threads.ReceiveThread;
import network.core.connections.threads.SendThread;
import network.framework.format.Mail;

public class ClientConnection implements Identifiable {
	
	private static final Long DELAY = 1800000l;
	
    private String playerId;
    private String gameId;
    private Socket connection;
    private HeartbeatValue heartbeatVal;
    private Heartbeat heartbeat;
    private LinkedBlockingQueue<Mail> outgoingMessages;
    private BlockingQueue<Message> incomingMessages;
    private ConnectionThread sendThread;
    private ConnectionThread receiveThread;
    
    public ClientConnection(String playerId, BlockingQueue<Message> receivedMessageQueue, Socket connection) throws IOException  {
        this.playerId = playerId;
    	this.connection = connection;
    	heartbeatVal = new HeartbeatValue();
        incomingMessages = receivedMessageQueue;
        outgoingMessages = new LinkedBlockingQueue<Mail>();
        sendThread =  new SendThread(connection, outgoingMessages);
        receiveThread = new ReceiveThread(playerId, connection, incomingMessages);
        sendThread.start();
        receiveThread.start();
        initializeHeartbeat();
    }
    
    public String getId() {
        return playerId;
    }
    
    public String getGameId() {
    	return gameId;
    }
    
    public void setGameId(String gameId) {
    	this.gameId = gameId;
    }
    
    public void close() {
    	sendThread.close();
    	receiveThread.close();
        try {
        	connection.close();
        } catch (IOException e) {}
    }
    
    public void send(Mail obj) {
        outgoingMessages.add(obj);
    }

	@Override
	public String getUniqueID() {
		return playerId;
	}

	@Override
	public Identifiable getCopy() {
		return null;
	} 
	
	private void initializeHeartbeat() {
		heartbeat = new Heartbeat(DELAY) {

			@Override
			public void heartbeat() {
				// TODO Auto-generated method stub
				
			}			
		};
	}
}
