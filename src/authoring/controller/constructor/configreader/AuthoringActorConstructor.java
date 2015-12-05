package authoring.controller.constructor.configreader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.actors.ActorPropertyMap;

public class AuthoringActorConstructor {

	private Map<String, ActorObject> actorMap;

	public AuthoringActorConstructor() {
		load();
	}

	private void load() {
		actorMap = new HashMap<String, ActorObject>();
		for (String actor : AuthoringConfigManager.getInstance().getKeyList(ResourceType.ACTORS)) {
			actorMap.put(actor, new ActorObject(actor, AuthoringConfigManager.getInstance().getPropertyList(actor)));
		}
	}

	/**
	 * Returns the list of actors. 
	 * 
	 * @return List<String>
	 */
	public List<String> getActorList() {
		return new ArrayList<String>(actorMap.keySet());
	}
	
	/**
	 * Returns the list of properties for a given actor. 
	 * 
	 * @param actor
	 * @return List<String>
	 */
	public List<String> getPropertyList(String actor) {
		return actorMap.get(actor).propertyMap.getPropertyList();
	}

	/**
	 * Returns the default value for a property for a given actor. 
	 * 
	 * @param actor
	 * @param property
	 * @return String
	 */
	public String getDefaultPropertyValue(String actor, String property) {
		return actorMap.get(actor).propertyMap.getPropertyValue(property);
	}

	/**
	 * Returns the ActorPropertyMap for a given actor. 
	 * 
	 * @param actor
	 * @return ActorPropertyMap
	 */
	public ActorPropertyMap getActorPropertyMap(String actor) {
		return actorMap.get(actor).propertyMap;
	}

	/**
	 * Sets the property for a given actor to value. 
	 * 
	 * @param actor
	 * @param property
	 * @param value
	 */
	public void setPropertyValue(String actor, String property, String value) {
		actorMap.get(actor).propertyMap.addProperty(property, value);
	}

	/**
	 * Returns the self trigger list for an actor. 
	 * 
	 * @param actor
	 * @return
	 */
	public List<String> getTriggerList(String actor) {
		return getTriggerList(actor, new String[]{});
	}

	/**
	 * Returns the trigger list between two or more actors. 
	 * 
	 * @param actor
	 * @param otherActors
	 * @return List<String>
	 */
	public List<String> getTriggerList(String actor, String... otherActors) {	
		return getListForEverything(ResourceType.TRIGGERS, actor, otherActors);
	}

	/**
	 * Returns the actions for a single actor. 
	 * 
	 * @param actor
	 * @return List<String>
	 */
	public List<String> getActionList(String actor) {
		return getListForEverything(ResourceType.ACTIONS, actor, new String[]{});
	}
	
	/**
	 * 
	 * @param actor
	 * @param otherActors
	 * @return
	 */
	
	public List<String> getActionList(String actor, String...otherActors) {
		return getListForEverything(ResourceType.ACTIONS, actor, otherActors);
	}
	
	private List<String> getListForEverything(String type, String actor, String...otherActors) {	
		List<String> ret = AuthoringConfigManager.getInstance().getConfigList(actor, type);
		removeInvalidInstances(type, ret, otherActors);;
		return ret;
	}	
	
	private void removeInvalidInstances(String type, List<String> actions, String...otherActors) {
		List<String> toRemove = new ArrayList<String>();
		
		for(String action : actions) {
			Integer val = new Integer(AuthoringConfigManager.getInstance().getTypeInfo(type, action, ResourceType.NUM_ACTORS));
			if(val != otherActors.length) {
				toRemove.add(action);
			} else {
				Integer count = 0;
				for(String actor : otherActors) {
					List<String> requiredProperties = AuthoringConfigManager.getInstance()
							.getRequiredPropertyList(type, String.format("%s.%s", ResourceType.ACTORS, count++));
					List<String> actorProperties = getPropertyList(actor);
					if(!requiredProperties.isEmpty() && !actorProperties.containsAll(requiredProperties)) {
						toRemove.add(action);
						break;
					}
				}
			}
			actions.removeAll(toRemove);
		}
	}

	private class ActorObject {
		private ActorPropertyMap propertyMap;

		private ActorObject(String actor, List<String> propertyList) {
			this.propertyMap = new ActorPropertyMap();
			load(actor, propertyList);
		}

		private void load(String actor, List<String> propertyList) {
			for (String s : propertyList) {
				propertyMap.addProperty(s, AuthoringConfigManager.getInstance().getDefaultPropertyValue(actor, s));
			}
		}
	}
}
