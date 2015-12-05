package authoring.controller.constructor.levelwriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.controller.constructor.configreader.AuthoringConfigManager;
import authoring.controller.constructor.configreader.ResourceType;
import authoring.model.actions.IAction;
import authoring.model.triggers.ITriggerEvent;
import voogasalad.util.reflection.Reflection;

public class MapConstructor {

	private Map<String, ITriggerEvent> triggerMap;
	private Map<String, IAction> actionMap;
	
	public MapConstructor() {
		triggerMap = new HashMap<String, ITriggerEvent>();
		actionMap = new HashMap<String, IAction>();		
		addValueToMap(triggerMap, AuthoringConfigManager.getInstance().getKeyList(ResourceType.TRIGGERS));
		addValueToMap(actionMap, AuthoringConfigManager.getInstance().getKeyList(ResourceType.ACTIONS));
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

	private <T> void addValueToMap(Map<String, T> map, List<String> actions) {
		for (String action : actions) {
			addValueToMap(map, action);
		}
	}
	
	private <T> void addValueToMap(Map<String, T> map, String action) {
		if (!map.containsKey(action)) {
			map.put(action, (T) Reflection.createInstance(action));
		}
	}
}
