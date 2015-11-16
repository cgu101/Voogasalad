package authoring.model.bundles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

/**
 * Bundle Class
 * 
 * @author Austin
 *
 * @param <V>
 */

public class Bundle<V extends Identifiable> extends Observable implements Collectable<V> {
	private Map<String, V> components;

	public Bundle() {
		components = new HashMap<String, V>();
	}
	public Bundle(Map<String, V> components) {
		this.components = components;
	}
	
	@Override
	public int getSize() {
		return components.size();
	}
	@Override
	public void add(V value) {
		components.put(value.getUniqueID(), value);
		update(components);
	}

	public void add(Map<String, V> otherComponents) {
		components.putAll(otherComponents);
		update(components);
	}
	
	public void remove(String componentID) {
		components.remove(componentID);
		update(components);
	}

	@Override
	public Iterator<V> iterator() {
		return components.values().iterator();
	}

	protected void update(Object o) {
		setChanged();
		notifyObservers(o);
	}

	public Map<String, V> getComponents() {
		return components;
	}
}