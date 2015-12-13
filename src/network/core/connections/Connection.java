package network.core.connections;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.connections.heartbeat.Heartbeat;
import network.core.connections.threads.ConnectionThread;
import network.core.connections.threads.ReceiveThread;
import network.core.connections.threads.SendThread;
import network.core.messages.Message;
import network.core.messages.format.Request;
import network.exceptions.StreamException;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Maintains the send and receive thread, and each connection gets the instance.
 */

public class Connection implements IDistinguishable, ICloseable {
	
	private static final Long DELAY = 1800000l;
	
	private String id;
    private Socket connection;
    private Heartbeat heartbeat;
    private LinkedBlockingQueue<Message> outgoingMessages;
    private BlockingQueue<Message> incomingMessages;
    private ConnectionThread sendThread;
    private ConnectionThread receiveThread;
    
    public Connection(String id, BlockingQueue<Message> receivedMessageQueue, Socket connection) throws IOException  {
    	this.id = id;
    	this.connection = connection;
        incomingMessages = receivedMessageQueue;
        outgoingMessages = new LinkedBlockingQueue<Message>();
        sendThread =  new SendThread(connection, outgoingMessages);
        receiveThread = new ReceiveThread(id, connection, incomingMessages);
        sendThread.start();
        receiveThread.start();
        initializeHeartbeat();
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
	
	private void initializeHeartbeat() {
		heartbeat = new Heartbeat(DELAY) {

			@Override
			public void heartbeat() {
				// TODO Auto-generated method stub
			}			
		};
	}

	@Override
	public Boolean isClosed() {
		// TODO
		return null;
	}

	@Override
	public String getID() {
		return id;
	}
}
