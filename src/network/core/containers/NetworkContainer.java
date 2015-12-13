package network.core.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import network.core.connections.ICloseable;
import network.core.connections.IDistinguishable;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class NetworkContainer<T extends IDistinguishable & ICloseable> {

	private Map<String, T> container;
	
	public NetworkContainer() {
		container = new HashMap<String, T>();
	}
	
	public List<String> getKeys() {
		return new ArrayList<String>(container.keySet());
	}
	
	public T getObject(String identifier) {
		return container.get(identifier);
	}
		
	public void addObject(T t) {
		container.put(t.getID(), t);
	}
	
	public void removeObject(String id) {
		container.remove(id);
	}
	
	public void close() throws Exception {
		for(T t : container.values()) {
			t.close();
		}
	}
}
