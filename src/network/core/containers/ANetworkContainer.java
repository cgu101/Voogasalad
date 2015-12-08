package network.core.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.bundles.Identifiable;

public abstract class ANetworkContainer<T extends Identifiable> {

	private Map<String, T> container;
	
	public ANetworkContainer() {
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
	
	public abstract void heartbeat();
	
}
