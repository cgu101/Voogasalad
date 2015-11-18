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
			actorMap.put(s, new ActorObject(s, 
					AuthoringConfigManager.getInstance().getPropertyList(s),
					AuthoringConfigManager.getInstance().getConfigList(s, AuthoringConfigManager.SELF_TRIGGER),
					AuthoringConfigManager.getInstance().getConfigList(s, AuthoringConfigManager.EVENT_TRIGGER),
					AuthoringConfigManager.getInstance().getConfigList(s, AuthoringConfigManager.ONE_ACTOR_ACTIONS),
					AuthoringConfigManager.getInstance().getConfigList(s, AuthoringConfigManager.TWO_ACTOR_ACTIONS)));
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
		return actorMap.get(actor).selfTriggerList;
	}
	
	public List<String> getTriggerList(String aActor, String...otherActors) {		
		// Need to compare bActor's properties against
		return null;
	}

	public List<String> getActionList(String actor, String...otherActors) {
		// Need to add the action, then do the same thing from before
		return null;
	}	
	
	private class ActorObject {
		private String actor;
		private ActorPropertyMap propertyMap;
		private List<String> selfTriggerList;
		private List<String> eventTriggerList;
		private List<String> oneActorActionList;
		private List<String> twoActorActionList;
		
		private ActorObject(String actor, List<String> propertyList) {
			this.actor = actor;
			load(propertyList);
		}
		
		private ActorObject(String actor,
				List<String> propertyList,
				List<String> selfTriggerList,
				List<String> eventTriggerList, 
				List<String> oneActorActionList, 
				List<String> twoActorActionList) {
			propertyMap = new ActorPropertyMap();
			this.actor = actor;
			this.selfTriggerList = selfTriggerList;
			this.eventTriggerList = eventTriggerList;
			this.oneActorActionList = oneActorActionList;
			this.twoActorActionList = twoActorActionList;
			load(propertyList);
		}
		
		private void load(List<String> propertyList) {
			for(String s: propertyList) {
				propertyMap.addProperty(s, AuthoringConfigManager.getInstance().getDefaultPropertyValue(actor, s));
			}
		}		
	}
 
}
