package network.core.connections;

import java.io.Serializable;

import network.core.messages.IDMessageEncapsulation;
import network.core.messages.Message;
import network.core.messages.format.Request;

public interface ISendable {

    public void send(Request request, Serializable ser, String queueId);
    public void send(IDMessageEncapsulation msg);
    public void send(Message m);
    
}
