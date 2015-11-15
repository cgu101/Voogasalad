package authoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;

public class AuthoringConfigManager {

	private Map<String, ResourceBundle> actorMap;
	private Map<String, ResourceBundle> propertyMap;
	
	private static final String CONFIGURATION_DIR = "authoring/files";
	private static final String ACTORS = "actors";
	private static final String PROPERTIES= "properties";
	private static final String CONFIGURATION_FILE = "configuration";
	
	private static final String REG_EX = ",";
	private static final String SELF_TRIGGER = "selfTrigger";
	private static final String EVENT_TRIGGER = "eventTrigger";
	
	public AuthoringConfigManager() {
		actorMap = new HashMap<String, ResourceBundle>();
		propertyMap = new HashMap<String, ResourceBundle>();
		load();
	}
	
	
	// TO DO 
	private void load() {
		ResourceBundle myConfiguration = ResourceBundle.getBundle(CONFIGURATION_FILE);
		
	}
	
	private void loadMap(Map<String, ResourceBundle> map, List<String> names) {
		
		for(String s: names) {
			
		}
		
	}
	
	public List<String> getActorList() {
		return new ArrayList<String>(actorMap.keySet());
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
	
	private List<String> getTriggerList(String actor, String type) {
		List<String> triggerList = Arrays.asList(actorMap.get(actor).getString(SELF_TRIGGER).split(REG_EX));
		String[] propertyList = actorMap.get(actor).getString(type).split(REG_EX);
		for(String s: propertyList) {
			String[] toAdd = propertyMap.get(s).getString(type).split(REG_EX);
			triggerList.addAll(Arrays.asList(toAdd));
		}
		
		return triggerList;
	}

}
