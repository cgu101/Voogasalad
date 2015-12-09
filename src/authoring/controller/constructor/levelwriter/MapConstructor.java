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
	
	private Map<ResourceType, Map> itemsMap;
	
	public MapConstructor() {
		itemsMap = new HashMap<ResourceType, Map>();
		itemsMap.put(ResourceType.TRIGGERS, new HashMap<String, ITriggerEvent>());
		itemsMap.put(ResourceType.ACTIONS, new HashMap<String, IAction>());	
		addValueToMap(AuthoringConfigManager.getInstance().getKeyList(ResourceType.TRIGGERS.toString()), ResourceType.TRIGGERS);
		addValueToMap(AuthoringConfigManager.getInstance().getKeyList(ResourceType.ACTIONS.toString()), ResourceType.ACTIONS);
	}
	
	public void setItemsMap (Map<ResourceType, Map> map) {
		this.itemsMap = map;
	}
	
	/**
	 * Returns the trigger map. 
	 * 
	 * @return Map<String, ITriggerEvent>
	 */
	public Map<String, ITriggerEvent> getTriggerMap() {
		return itemsMap.get(ResourceType.TRIGGERS);
	}
	
	/**
	 * Returns the action map. 
	 * 
	 * @return Map<String, IAction> 
	 */
	public Map<String, IAction> getActionMap() {
		return itemsMap.get(ResourceType.ACTIONS);
	}
	
	public <T> void addValueToMap(String toAdd, ResourceType type) {
		Map<String, T> map = itemsMap.get(type);
		if (!map.containsKey(toAdd)) {
			map.put(toAdd, (T) Reflection.createInstance(toAdd));
		}
	}

	private <T> void addValueToMap(List<String> actions, ResourceType type) {
		for (String action : actions) {
			addValueToMap(itemsMap.get(type), action, type);
		}
	}
	
	private <T> void addValueToMap(Map<String, T> map, String action, ResourceType type) {
		if (!map.containsKey(action)) {
			String value = AuthoringConfigManager.getInstance().getTypeInfo(type.toString(), action, ResourceType.CLASS_NAME.toString()); 
			map.put(action, (T) Reflection.createInstance(value));
		}
	}
}
