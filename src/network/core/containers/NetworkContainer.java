package network.core.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.bundles.Identifiable;
import network.core.connections.ICloseable;

public class NetworkContainer<T extends Identifiable & ICloseable> {

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
		container.put(t.getUniqueID(), t);
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
