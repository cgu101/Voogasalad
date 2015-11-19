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

	public List<String> getActorList() {
		return new ArrayList<String>(actorMap.keySet());
	}

	public List<String> getPropertyList(String actor) {
		return actorMap.get(actor).propertyMap.getPropertyList();
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

	public List<String> getEventTriggerList(String actor, String... otherActors) {
		List<String> ret = new ArrayList<String>(actorMap.get(actor).eventTriggerList);
		removeInvalidInstances(ret, otherActors);
		return ret;
	}

	public List<String> getActionList(String actor) {
		return new ArrayList<String>(actorMap.get(actor).oneActorActionList);
	}
	
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
