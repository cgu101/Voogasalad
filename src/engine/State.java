package engine;

import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
/**
 * A save-state of a game. 
 * Contains a property bundle for metadata and a ActorGroups containing the state of actors.
 * @author Austin
 */
public class State {
	private ActorGroups myActorMap;
	private Bundle<Property<?>> myPropertyBundle;
	
	public State () {
		myPropertyBundle = new Bundle<Property<?>>();
		myActorMap = new ActorGroups();
	}
	public State (Bundle<Property<?>> propertyBundle, ActorGroups actorMap) {
		myPropertyBundle = propertyBundle;
		myActorMap = actorMap;
	}
	public State(State state) {
		myPropertyBundle = new Bundle<Property<?>>(state.getPropertyBundle());
		myActorMap = new ActorGroups(state.getActorMap());
	}

	public ActorGroups getActorMap() {
		return myActorMap;
	}
	protected void setActorMap(ActorGroups map) {
		this.myActorMap = map;
	}
	
	/**
	 * @param key
	 * @return property value from property bundle
	 */
	public Property<?> getProperty (String key) {
		return myPropertyBundle.get(key);
	}
	public Bundle<Property<?>> getPropertyBundle () {
		return myPropertyBundle;
	}
	protected void merge(State nextState) {
		this.myPropertyBundle = nextState.getPropertyBundle();
		this.myActorMap = nextState.getActorMap();
	}
}
