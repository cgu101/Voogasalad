package authoring.model.bundles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import authoring.model.actors.Actor;

public class Group<V extends Identifiable> extends Observable implements Collectable<V>{
	private List<V> elements;
	public Group() {
		elements = new ArrayList<V>();
	}
	public Group(Group<V> value) {
		this.elements = new ArrayList<V>();
		for (V element : value.getElements()) {
			this.elements.add(element);
		}
	}
	@Override
	public int getSize() {
		return elements.size();
	}
	@Override
	public void add(V value) {
		elements.add(0, value);
		update(elements);
	}

	public void add(List<V> otherList) {
		List<V> retList = new ArrayList<V>(otherList);
		retList.addAll(otherList);
		elements = retList;
		update(elements);
	}
	
	// TODO: get rid of this
	public void remove(V value) {
		elements.remove(value);
		update(elements);
	}

	@Override
	public Iterator<V> iterator() {
		// TODO
		return elements.iterator();
	}

	protected void update(Object o) {
		setChanged();
		notifyObservers(o);
	}
	
	private List<V> getElements () {
		return elements;
	}
}
