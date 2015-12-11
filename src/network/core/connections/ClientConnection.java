package network.core.connections;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import authoring.model.bundles.Identifiable;
import network.core.connections.threads.ConnectionThread;
import network.core.connections.threads.ReceiveThread;
import network.core.connections.threads.SendThread;
import network.core.messages.Message;
import network.deprecated.ForwardedMessage;
import network.framework.format.Mail;

public class ClientConnection implements Identifiable, Closeable {
	
	private static final Long DELAY = 1800000l;
	
    private Integer playerId;
    private String gameId;
    private Socket connection;
    private HeartbeatValue heartbeatVal;
    private Heartbeat heartbeat;
    private LinkedBlockingQueue<Object> outgoingMessages;
    private BlockingQueue<Object> incomingMessages;
    private ConnectionThread sendThread;
    private ConnectionThread receiveThread;
    
    public ClientConnection(Integer playerId, BlockingQueue<Object> receivedMessageQueue, Socket connection) throws IOException  {
        this.playerId = playerId;
    	this.connection = connection;
    	heartbeatVal = new HeartbeatValue();
        incomingMessages = receivedMessageQueue;
        outgoingMessages = new LinkedBlockingQueue<Object>();
        sendThread =  new SendThread(connection, outgoingMessages);
        receiveThread = new ReceiveThread(playerId, connection, incomingMessages);
        sendThread.start();
        receiveThread.start();
        initializeHeartbeat();
    }
    
    public Integer getId() {
        return playerId;
    }
    
    public String getGameId() {
    	return gameId;
    }
    
    public void setGameId(String gameId) {
    	this.gameId = gameId;
    }
    
    @Override
    public void close() {
    	sendThread.close();
    	receiveThread.close();
        try {
        	connection.close();
        } catch (IOException e) {}
    }
    
    public void send(Object obj) {
        outgoingMessages.add(obj);
    }

	@Override
	public String getUniqueID() {
		return playerId.toString();
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
	
	public HeartbeatValue getHeartbeatValue() {
		return heartbeatVal;
	}
}
