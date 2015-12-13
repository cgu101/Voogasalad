package network.core.connections;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import authoring.model.bundles.Identifiable;
import network.core.connections.threads.ConnectionThread;
import network.core.connections.threads.ReceiveThread;
import network.core.connections.threads.SendThread;
import network.core.messages.Message;
import network.exceptions.StreamException;
import network.framework.format.Request;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Maintains the send and receive thread, and each connection gets the instance.
 */

public class ClientConnection implements Identifiable, ICloseable {
	
	private static final Long DELAY = 1800000l;
	
    private String clientId;
    private String gameId;
    private Socket connection;
    private HeartbeatValue heartbeatVal;
    private Heartbeat heartbeat;
    private LinkedBlockingQueue<Message> outgoingMessages;
    private BlockingQueue<Message> incomingMessages;
    private ConnectionThread sendThread;
    private ConnectionThread receiveThread;
    
    public ClientConnection(String clientId, BlockingQueue<Message> receivedMessageQueue, Socket connection) throws IOException  {
        this.clientId = clientId;
    	this.connection = connection;
    	heartbeatVal = new HeartbeatValue();
        incomingMessages = receivedMessageQueue;
        outgoingMessages = new LinkedBlockingQueue<Message>();
        sendThread =  new SendThread(connection, outgoingMessages);
        receiveThread = new ReceiveThread(clientId, connection, incomingMessages);
        sendThread.start();
        receiveThread.start();
        initializeHeartbeat();
    }
    
    public String getId() {
        return clientId;
    }
    
    public String getGameId() {
    	return gameId;
    }
    
    public void setGameId(String gameId) {
    	this.gameId = gameId;
    }
    
    @Override
    public void close() throws StreamException {
    	sendThread.close();
    	receiveThread.close();
        try {
        	connection.close();
        } catch (IOException e) {}
    }
    
    public void send(Request request, Serializable ser, String id) {
    	Message msg = new Message(ser, request, id);
        send(msg);
    }
    
    public void send(Message msg) {
        outgoingMessages.add(msg);
    }

	@Override
	public String getUniqueID() {
		return clientId;
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

	@Override
	public Boolean isClosed() {
		// TODO Auto-generated method stub
		return null;
	}
}
