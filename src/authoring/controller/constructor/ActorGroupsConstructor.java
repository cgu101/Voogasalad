package authoring.controller.constructor;

import java.util.Arrays;
import java.util.List;

import authoring.model.actors.ActorGroups;
import authoring.model.actors.ActorPropertyMap;

public class ActorGroupsConstructor {
	
	private ActorGroups actorGroups;
		
	ActorGroupsConstructor() {
		actorGroups = new ActorGroups();
	}
	
	public ActorGroups getActorGroups() {
		return actorGroups;
	}
	
	public void updateActor(String id, ActorPropertyMap propertyMap) {
		updateActor(Arrays.asList(new String[] {id}), propertyMap);
	}
	
	public void updateActor(List<String> ids, ActorPropertyMap propertyMap) {
		for(String id: ids) {
			// If the actor does not exist, 
			
			// If the actor exists, get its property list, create new instances of properties
			// in property map, and add these to the property list. 
		}
	}
	
}
