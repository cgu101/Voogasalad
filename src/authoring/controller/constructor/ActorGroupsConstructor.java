package authoring.controller.constructor;

import java.util.Arrays;
import java.util.List;

import authoring.controller.AuthoringConfigManager;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.actors.ActorPropertyMap;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import voogasalad.util.reflection.Reflection;

public class ActorGroupsConstructor {
	
	private ActorGroups actorGroups;
		
	ActorGroupsConstructor() {
		actorGroups = new ActorGroups();
	}
	
	public ActorGroups getActorGroups() {
		return actorGroups;
	}
	
	public void updateActor(String className, String id, ActorPropertyMap propertyMap) {
		updateActor(className, Arrays.asList(new String[] {id}), propertyMap);
	}
	
	public void updateActor(String className, List<String> ids, ActorPropertyMap propertyMap) {		
		Bundle<Actor> actorBundle = actorGroups.addGroup(className);		
		Bundle<Property<?>> properties = getPropertyBundle(propertyMap);	
		
		for(String id: ids) {
			if(actorBundle.getComponents().containsKey(id)) {
				for(Property<?> p: properties) {
					actorBundle.getComponents().get(id).getProperties().add(p);
				}
			} else {
				actorBundle.add(new Actor(properties, id));
			} 
		}
	}
	
	public void deleteActor(String className, List<String> ids) {
		Bundle<Actor> actorBundle = actorGroups.addGroup(className);
		if(actorBundle != null) {
			for(String id: ids) {
				actorBundle.remove(id);
			}
		}
	}
	
	private Bundle<Property<?>> getPropertyBundle(ActorPropertyMap propertyMap) {
		Bundle<Property<?>> ret = new Bundle<Property<?>>();
		for(String p : propertyMap.getPropertyList()) {
			String type = AuthoringConfigManager.getInstance().getPropertyType(p);
			Object newObject = Reflection.createInstance(type, propertyMap.getPropertyValue(p));
			ret.add(new Property(p, newObject));
		}
		return ret;
	}
}
