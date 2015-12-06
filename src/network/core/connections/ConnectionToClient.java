package network.core.connections;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import network.framework.format.Mail;
import network.game.Message;

public class ConnectionToClient {
	
    private Integer playerId;
    private LinkedBlockingQueue<Mail> outgoingMessages;
    private BlockingQueue<Message> incomingMessages;
    private Socket connection;
    private ConnectionThread sendThread;
    private ConnectionThread receiveThread;
    
    public ConnectionToClient(Integer playerId, BlockingQueue<Message> receivedMessageQueue, Socket connection)  {
        this.playerId = playerId;
    	this.connection = connection;
        incomingMessages = receivedMessageQueue;
        outgoingMessages = new LinkedBlockingQueue<Mail>();
        sendThread =  new SendThread(connection, outgoingMessages);
        receiveThread = new ReceiveThread(playerId, connection, incomingMessages);
        sendThread.start();
        receiveThread.start();
    }
    
    public int getPlayer() {
        return playerId;
    }
    
    public void close() {
    	sendThread.close();
    	receiveThread.close();
    	sendThread.interrupt();
    	receiveThread.interrupt();
        try {
        	connection.close();
        } catch (IOException e) {}
    }
    
    public void send(Mail obj) {
        outgoingMessages.add(obj);
    }
}
