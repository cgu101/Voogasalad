package authoring.controller.constructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.controller.AuthoringConfigManager;
import authoring.model.actors.ActorPropertyMap;

public class AuthoringActorConstructor {

	private Map<String, ActorObject> actorMap;
	private static final String ALT = "b";

	AuthoringActorConstructor() {
		actorMap = new HashMap<String, ActorObject>();
		load();
	}

	private void load() {
		for (String s : AuthoringConfigManager.getInstance().getActorList()) {
			actorMap.put(s,
					new ActorObject(s, AuthoringConfigManager.getInstance().getPropertyList(s),
							AuthoringConfigManager.getInstance().getConfigList(s, AuthoringConfigManager.SELF_TRIGGER),
							AuthoringConfigManager.getInstance().getConfigList(s, AuthoringConfigManager.EVENT_TRIGGER),
							AuthoringConfigManager.getInstance().getConfigList(s,AuthoringConfigManager.ONE_ACTOR_ACTIONS),
							AuthoringConfigManager.getInstance().getConfigList(s, AuthoringConfigManager.TWO_ACTOR_ACTIONS)));
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
	public List<String> getSelfTriggerList(String actor) {
		return actorMap.get(actor).selfTriggerList;
	}

	/**
	 * Returns the trigger list between two or more actors. 
	 * 
	 * @param actor
	 * @param otherActors
	 * @return List<String>
	 */
	public List<String> getEventTriggerList(String actor, String... otherActors) {
		List<String> ret = new ArrayList<String>(actorMap.get(actor).eventTriggerList);
		removeInvalidInstances(ret, otherActors);
		return ret;
	}

	/**
	 * Returns the actions for a single actor. 
	 * 
	 * @param actor
	 * @return List<String>
	 */
	public List<String> getActionList(String actor) {
		return new ArrayList<String>(actorMap.get(actor).oneActorActionList);
	}
	
	/**
	 * Returns the actions between two actors. 
	 * 
	 * @param actor
	 * @param otherActors
	 * @return List<String>
	 */
	public List<String> getTwoActorActionList(String actor, String...otherActors) {
		List<String> ret = new ArrayList<String>(actorMap.get(actor).twoActorActionList);
		removeInvalidInstances(ret, otherActors);
		ret.addAll(getActionList(actor));
		return ret;
	}
	
	private void removeInvalidInstances(List<String> actions, String...otherActors) {
		List<String> toRemove = new ArrayList<String>();
		for(String actor: otherActors) {
			List<String> actorProperties = getPropertyList(actor);
			for(String s: actions) {
				List<String> requiredProperties = AuthoringConfigManager.getRequiredPropertyList(String.format("%s.%s", s, ALT));
				if(!requiredProperties.isEmpty() && !actorProperties.containsAll(requiredProperties)) {
					toRemove.add(s);
				}
			}
			actions.removeAll(toRemove);
		}
	}

	private class ActorObject {
		private String actor;
		private ActorPropertyMap propertyMap;
		private List<String> selfTriggerList;
		private List<String> eventTriggerList;
		private List<String> oneActorActionList;
		private List<String> twoActorActionList;


		private ActorObject(String actor, List<String> propertyList, List<String> selfTriggerList,
				List<String> eventTriggerList, List<String> oneActorActionList, List<String> twoActorActionList) {
			this.propertyMap = new ActorPropertyMap();
			this.actor = actor;
			this.selfTriggerList = selfTriggerList;
			this.eventTriggerList = eventTriggerList;
			this.oneActorActionList = oneActorActionList;
			this.twoActorActionList = twoActorActionList;
			load(propertyList);
		}

		private void load(List<String> propertyList) {
			for (String s : propertyList) {
				propertyMap.addProperty(s, AuthoringConfigManager.getInstance().getDefaultPropertyValue(actor, s));
			}
		}
	}

}
