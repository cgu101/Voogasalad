package network.core.messages;

import java.io.Serializable;

import network.framework.format.Request;

public class ServerMessage extends Message {

	private static final long serialVersionUID = 8605541165705784067L;
	
	private Integer clientId;
	
	public ServerMessage(Integer clientId, Serializable payload, Request request, String id) {
		super(payload, request, id);
		this.clientId = clientId;
	}
	
	public Integer getClientId() {
		return clientId;
	}
}
