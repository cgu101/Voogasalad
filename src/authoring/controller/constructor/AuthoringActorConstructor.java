package authoring.controller.constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.controller.AuthoringConfigManager;
import authoring.model.actors.ActorPropertyMap;

public class AuthoringActorConstructor {
	
	private Map<String, ActorObject> actorMap;
	
	AuthoringActorConstructor() {
		actorMap = new HashMap<String, ActorObject>();
		load();
	}
	
	private void load() {
		for(String s: AuthoringConfigManager.getInstance().getActorList()) {
			actorMap.put(s, new ActorObject(s, AuthoringConfigManager.getInstance().getPropertyList(s)));
		}
	}
	
	public List<String> getActorList() {
		return new ArrayList<String>(actorMap.keySet());
	}
	
	public List<String> getPropertyList(String actor) {
		return  actorMap.get(actor).propertyMap.getPropertyList();
	}
	
	public String getDefaultPropertyValue(String actor, String property) {
		return actorMap.get(actor).propertyMap.getPropertyValue(property);
	}
	
	public ActorPropertyMap getActorPropertyMap(String actor) {
		return actorMap.get(actor).propertyMap;
	}
	
	public void setPropertyValue(String actor, String property, String value) {
		actorMap.get(actor).propertyMap.addProperty(property, value);
	}
	
	public List<String> getSelfTriggerList(String actor) {
		return AuthoringConfigManager.getInstance().getSelfTriggerList(actor);
	}
	
	public List<String> getEventTriggerList(String actor) {
		return AuthoringConfigManager.getInstance().getEventTriggerList(actor);
	}

	public List<String> getActionList(String actor) {
		return AuthoringConfigManager.getInstance().getActionList(actor);
	}	
	
	private class ActorObject {
		private String actor;
		private ActorPropertyMap propertyMap;
		
		private ActorObject(String actor, List<String> propertyList) {
			this.actor = actor;
			load(propertyList);
		}
			
		private void load(List<String> propertyList) {
			for(String s: propertyList) {
				propertyMap.addProperty(s, AuthoringConfigManager.getInstance().getDefaultPropertyValue(actor, s));
			}
		}		
	}
 
}
