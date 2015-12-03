package authoring.controller.constructor.levelwriter;

import java.util.Arrays;
import java.util.List;

import authoring.controller.constructor.configreader.AuthoringConfigManager;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.actors.ActorPropertyMap;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import voogasalad.util.reflection.Reflection;

/**
 * @author Chris
 *
 */
public class ActorGroupsConstructor {
	
	private ActorGroups actorGroups;
	
	private static final String GROUP_ID = "groupID";
	
	/**
	 * Empty contructor for ActorGroupsConstructor
	 */
	ActorGroupsConstructor() {
		actorGroups = new ActorGroups();
	}
	
	/**
	 * Returns the ActorGroups instance that has been built by the user. 
	 * @return ActorGroups
	 */
	public ActorGroups getActorGroups() {
		return actorGroups;
	}
	
	/**
	 * This method calls updateActor(List<String> ids, ActorPropertyMap propertyMap)
	 * 
	 * @param id
	 * @param propertyMap
	 */
	public void updateActor(String id, ActorPropertyMap propertyMap) {
		updateActor(Arrays.asList(new String[] {id}), propertyMap);
	}
	
	/**
	 * This method will create a new instanc of an actor if it doesn't exist. Otherwise, it will update all of the actors
	 * identified by the id's in the String list. 
	 * 
	 * @param ids
	 * @param propertyMap
	 */
	public void updateActor(List<String> ids, ActorPropertyMap propertyMap) {		
		Bundle<Actor> actorBundle = actorGroups.addGroup(propertyMap.getPropertyValue(GROUP_ID));		
		Bundle<Property<?>> properties = getPropertyBundle(propertyMap);	
		
		for(String id: ids) {
			if(actorBundle.getComponents().containsKey(id)) {
				for(Property<?> p: properties) {
					actorBundle.getComponents().get(id).getProperties().add(p);
				}
			} else {
				actorGroups.addActor(new Actor(properties, id));
			} 
		}
	}
	
	/**
	 * This method will delete all actors identified by id for a given String className.
	 * 
	 * @param className
	 * @param ids
	 */
	public void deleteActor(String className, List<String> ids) {
		Bundle<Actor> actorBundle = actorGroups.addGroup(className);
		if(actorBundle != null) {
			for(String id: ids) {
				actorBundle.remove(id);
			}
		}
	}
	
	/**
	 * This method will return an individual actor identified by its className and id.
	 * 
	 * @param className
	 * @param id
	 * @return ACtor
	 */
	public Actor getActor(String className, String id) {
		Bundle<Actor> actorBundle = actorGroups.addGroup(className);
		return actorBundle.get(id);
	}
	
	private Bundle<Property<?>> getPropertyBundle(ActorPropertyMap propertyMap) {
		Bundle<Property<?>> ret = new Bundle<Property<?>>();
		for(String p : propertyMap.getPropertyList()) {
			String type = AuthoringConfigManager.getInstance().getPropertyType(p);
			System.out.println(type);
			Object newObject = Reflection.createInstance(type, propertyMap.getPropertyValue(p));
			ret.add(new Property(p, newObject));
		}
		return ret;
	}
}
