package authoring.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AuthoringConfigManager {

	private Map<String, ResourceBundle> actorMap;
	private Map<String, ResourceBundle> propertyMap;
	
	private static final String CONFIGURATION_DIR = "authoring/files/%s";
	private static final String ACTORS = "actors";
	private static final String PROPERTIES= "properties";
	private static final String CONFIGURATION = "configuration";
	private static final String DIRECTORY_FORMAT = "%s/%s";
	
	private static final String REG_EX = ",";
	private static final String SELF_TRIGGER = "selfTrigger";
	private static final String EVENT_TRIGGER = "eventTrigger";
	private static final String TYPE = "type";
	private static final String ACTIONS = "actions";
	
	private static final AuthoringConfigManager myManager = new AuthoringConfigManager();
	
	private AuthoringConfigManager() {
		load();
	}
	
	private void load() {
		actorMap = new HashMap<String, ResourceBundle>();
		propertyMap = new HashMap<String, ResourceBundle>();
		ResourceBundle myConfiguration = ResourceBundle.getBundle(String.format(CONFIGURATION_DIR, CONFIGURATION));
		loadMap(actorMap, myConfiguration.getString(ACTORS).split(REG_EX), ACTORS);
		loadMap(propertyMap, myConfiguration.getString(PROPERTIES).split(REG_EX), PROPERTIES);
	}
	
	private void loadMap(Map<String, ResourceBundle> map, String[] names, String type) {		
		for(String s: names) {
			ResourceBundle toAdd = ResourceBundle.getBundle(
					String.format(CONFIGURATION_DIR, 
					String.format(DIRECTORY_FORMAT, type, s))
					);
			map.put(s, toAdd);
		}	
	}
	
	public static AuthoringConfigManager getInstance() {
		return myManager;
	}
	
	public void refresh() {
		load();
	}
	
	public List<String> getActorList() {
		return new ArrayList<String>(actorMap.keySet());
	}
	
	public List<String> getPropertyList() {
		return new ArrayList<String>(propertyMap.keySet());
	}

	public List<String> getPropertyList(String actor) {
		return  Arrays.asList(actorMap.get(actor).getString(PROPERTIES).split(REG_EX));
	}
	
	public String getDefaultPropertyValue(String actor, String property) {
		return actorMap.get(actor).getString(property);
	}
	
	public List<String> getSelfTriggerList(String actor) {
		return getTriggerList(actor, SELF_TRIGGER);
	}
	
	public List<String> getEventTriggerList(String actor) {
		return getTriggerList(actor, EVENT_TRIGGER);
	}
	
	public List<String> getActionList(String actor) {
		return getTriggerList(actor, ACTIONS);
	}
	
	public String getPropertyType(String property) {
		return propertyMap.get(property).getString(TYPE);
	}
	
	private List<String> getTriggerList(String actor, String type) {
		List<String> triggerList = Arrays.asList(actorMap.get(actor).getString(type).split(REG_EX));
		String[] propertyList = actorMap.get(actor).getString(type).split(REG_EX);
		for(String s: propertyList) {
			if(propertyMap.containsKey(s)) {
				String[] toAdd = propertyMap.get(s).getString(type).split(REG_EX);
				triggerList.addAll(Arrays.asList(toAdd));
			}
		}
		
		return triggerList;
	}
}
