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
	
	public State (Bundle<Property<?>> propertyBundle, ActorGroups actorMap) {
		myPropertyBundle = propertyBundle;
		myActorMap = actorMap;
	}
	
	public ActorGroups getActorMap() {
		return myActorMap;
	}
	
	/**
	 * @param key
	 * @return property value from property bundle
	 */
	public Property<?> getProperty (String key) {
		return myPropertyBundle.get(key);
	}
}
