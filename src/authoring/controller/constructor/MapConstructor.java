package authoring.controller.constructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.actions.IAction;
import authoring.model.triggers.ITriggerEvent;
import voogasalad.util.reflection.Reflection;

public class MapConstructor {

	private Map<String, ITriggerEvent> triggerMap;
	private Map<String, IAction> actionMap;
	
	public MapConstructor() {
		triggerMap = new HashMap<String, ITriggerEvent>();
		actionMap = new HashMap<String, IAction>();
		
		// Need to actually add all objects to them
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
	
}
