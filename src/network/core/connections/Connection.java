package network.core.connections;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import network.core.connections.threads.ConnectionThread;
import network.core.connections.threads.ReceiveThread;
import network.core.connections.threads.SendThread;
import network.core.messages.Message;
import network.core.messages.IDMessage;
import network.core.messages.format.Request;
import network.exceptions.StreamException;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Maintains the send and receive thread, and each connection gets the instance.
 */

public class Connection implements IDistinguishable, ICloseable {
	
	private String connectionId;
    private Socket connection;
    private LinkedBlockingQueue<IDMessage> outgoingMessages;
    private BlockingQueue<IDMessage> incomingMessages;
    private ConnectionThread sendThread;
    private ConnectionThread receiveThread;
    
    public Connection(String connectionId, BlockingQueue<IDMessage> receivedMessageQueue, Socket connection) throws IOException  {
    	this.connectionId = connectionId;
    	this.connection = connection;
        incomingMessages = receivedMessageQueue;
        outgoingMessages = new LinkedBlockingQueue<IDMessage>();
        sendThread =  new SendThread(connection, outgoingMessages);
        receiveThread = new ReceiveThread(connection, incomingMessages);
        sendThread.start();
        receiveThread.start();
    }
    
    public Connection(BlockingQueue<IDMessage> receivedMessageQueue, Socket connection) throws IOException  {
    	this(Connection.class.getName(), receivedMessageQueue, connection);
    }
    
    @Override
    public void close() throws StreamException {
    	sendThread.close();
    	receiveThread.close();
        try {
        	connection.close();
        } catch (IOException e) {}
    }
    
    public void send(Request request, Serializable ser, String queueId) {
    	IDMessage msg = new IDMessage(connectionId, new Message(ser, request, queueId));
    	
        send(msg);
    }
    
    public void send(IDMessage msg) {
        outgoingMessages.add(msg);
    }
    
    public void setId(String connectionId) {
    	this.connectionId = connectionId;
    }

	@Override
	public Boolean isClosed() {
		// TODO
		return null;
	}

	@Override
	public String getID() {
		return connectionId;
	}
}
