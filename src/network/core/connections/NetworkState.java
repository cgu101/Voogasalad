package network.core.connections;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class NetworkState implements IDistinguishable, ICloseable  {
		
	private String gameIdentifier;
	private Serializable state;
	private List<String> clients;
	
	public NetworkState(String gameIdentifier, Serializable state, String client) {
		this.gameIdentifier = gameIdentifier;
		this.state = state;
		clients = new ArrayList<String>();
		clients.add(client);
	}
	
	public void addClient(String client) {
		clients.add(client);
	}
	
	public void removeClient(Integer client) {
		clients.remove(client);
	}
	
	public List<String> getClients() {
		return clients;
	}
	
	public Serializable getState() {
		return state;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public Boolean isClosed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getID() {
		return gameIdentifier;
	}
		
}
