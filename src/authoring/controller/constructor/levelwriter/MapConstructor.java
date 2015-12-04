package authoring.controller.constructor.levelwriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import authoring.controller.constructor.configreader.AuthoringConfigManager;
import authoring.model.actions.IAction;
import authoring.model.triggers.ITriggerEvent;
import voogasalad.util.reflection.Reflection;

public class MapConstructor {

	private Map<String, ITriggerEvent> triggerMap;
	private Map<String, IAction> actionMap;
	
	public MapConstructor() {
		initializeTriggerMap();
		initializeActionMap();
	}
	
	private void initializeTriggerMap() {
		triggerMap = new HashMap<String, ITriggerEvent>();
		addTriggerToMap(getSetOfValues(AuthoringConfigManager.SELF_TRIGGER, AuthoringConfigManager.EVENT_TRIGGER));
	}
	
	private void initializeActionMap() {
		actionMap = new HashMap<String, IAction>();
		addActionsToMap(getSetOfValues(AuthoringConfigManager.ONE_ACTOR_ACTIONS, AuthoringConfigManager.TWO_ACTOR_ACTIONS));		
	}
	
	private List<String> getSetOfValues(String a, String b) {
		List<String> ret = new ArrayList<String>();
		Set<String> container = new HashSet<String>();
		List<String> actors = AuthoringConfigManager.getInstance().getActorList();
		for(String actor : actors) {
			container.addAll(AuthoringConfigManager.getInstance().getConfigList(actor, a));
			container.addAll(AuthoringConfigManager.getInstance().getConfigList(actor, b));
		}
		ret.addAll(container);
		return ret;
	}
	
	
	/**
	 * Returns the trigger map. 
	 * 
	 * @return Map<String, ITriggerEvent>
	 */
	public Map<String, ITriggerEvent> getTriggerMap() {
		return triggerMap;
	}
	
	/**
	 * Returns the action map. 
	 * 
	 * @return Map<String, IAction> 
	 */
	public Map<String, IAction> getActionMap() {
		return actionMap;
	}
	
	public void addTriggerToMap(List<String> triggers) {
		for(String trigger : triggers) {
			addTriggerToMap(trigger);
		}
	}

	public void addTriggerToMap(String trigger) {
		if (!triggerMap.containsKey(trigger)) {
			triggerMap.put(trigger, (ITriggerEvent) Reflection.createInstance(trigger));
		}
	}

	public void addActionsToMap(List<String> actions) {
		for (String s : actions) {
			if (!actionMap.containsKey(s)) {
				actionMap.put(s, (IAction) Reflection.createInstance(s));
			}
		}
	}
	
	public void addActionsToMap(String action) {
		if (!actionMap.containsKey(action)) {
			actionMap.put(action, (IAction) Reflection.createInstance(action));
		}
	}
}
