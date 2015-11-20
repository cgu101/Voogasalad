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
	private ResourceBundle myConfiguration;
	
	private static final String CONFIGURATION_DIR = "authoring/files/%s";
	private static final String ACTORS = "actors";
	private static final String PROPERTIES= "properties";
	private static final String CONFIGURATION = "configuration";
	private static final String DIRECTORY_FORMAT = "%s/%s";
	private static final String REG_EX = ",";
	private static final String TYPE = "type";	

	public static final String SELF_TRIGGER = "selfTrigger";
	public static final String EVENT_TRIGGER = "eventTrigger";
	public static final String ONE_ACTOR_ACTIONS = "oneActorActions";
	public static final String TWO_ACTOR_ACTIONS = "twoActorActions";
	
	private static final AuthoringConfigManager myManager = new AuthoringConfigManager();
	
	private AuthoringConfigManager() {
		load();
	}
	
	private void load() {
		actorMap = new HashMap<String, ResourceBundle>();
		propertyMap = new HashMap<String, ResourceBundle>();
		myConfiguration = ResourceBundle.getBundle(String.format(CONFIGURATION_DIR, CONFIGURATION));
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
	
	/**
	 * This method returns the static instance of the AuthuringConfigManager.
	 * This class holds the information for the relationships between all actors, triggers, and actions.
	 * 
	 * @return AuthoringConfigManager
	 */
	public static AuthoringConfigManager getInstance() {
		return myManager;
	}
	
	/**
	 * This method will reload the configuration files and should be called whenever the configuration files
	 * change during a game. 
	 */
	public void refresh() {
		load();
	}
	
	/**
	 * This method will return a String array of all default Actors. 
	 * 
	 * @return List<String> 
	 */
	public List<String> getActorList() {
		return new ArrayList<String>(actorMap.keySet());
	}
	
	/**
	 * This method will return a String array of all default Properties. 
	 * 
	 * @return List<String> 
	 */	
	public List<String> getPropertyList() {
		return new ArrayList<String>(propertyMap.keySet());
	}
	
	/**
	 * This method will return a String array of all default Properties. 
	 * 
	 * @return List<String> 
	 */	
	public List<String> getPropertyList(String actor) {
		return splitString(actorMap.get(actor).getString(PROPERTIES));
	}

	/**
	 * This method will return the default value for a property mapped to an actor.
	 * 
	 * @param actor
	 * @param property
	 * @return String
	 */
	public String getDefaultPropertyValue(String actor, String property) {
		return actorMap.get(actor).getString(property);
	}
	
	/**
	 * This method will return the type for the given property. These types are primitives and are used
	 * for the instantiation of the property class. 
	 * 
	 * @param property
	 * @return String
	 */
	public String getPropertyType(String property) {
		return propertyMap.get(property).getString(TYPE);
	}
	
	/**
	 * This method will return a list of Strings representing the given type for the Actor. The different
	 * types are represented by the public constants and are: 
	 * 		public static final String SELF_TRIGGER = "selfTrigger";
	 *		public static final String EVENT_TRIGGER = "eventTrigger";
	 *		public static final String ONE_ACTOR_ACTIONS = "oneActorActions";
	 * 		public static final String TWO_ACTOR_ACTIONS = "twoActorActions";
	 * 
	 * @param actor
	 * @param type
	 * @return List<String>
	 */
	public List<String> getConfigList(String actor, String type) {
		List<String> triggerList = splitString(actorMap.get(actor).getString(type));
		List<String> propertyList = getPropertyList(actor);
		for(String s: propertyList) {
			if(propertyMap.containsKey(s)) {
				List<String> toAdd = splitString(propertyMap.get(s).getString(type));
				combineList(triggerList, checkIfAdditionIsPossible(propertyList, toAdd, actor));
			}
		}	
		return triggerList;
	}
	
	/**
	 * This method will return the properties required by an trigger or action.
	 * 
	 * @param instance
	 * @return List<String>
	 */
	public static List<String> getRequiredPropertyList(String instance) {
		return splitString(myManager.myConfiguration.getString(String.format("%s.%s", instance, PROPERTIES)));
	}
	
	private List<String> checkIfAdditionIsPossible(List<String> propertyList, List<String> toAdd, String actor) {
		List<String> ret = new ArrayList<String>();
		for(String add: toAdd) {
			if(!add.equals("")) {
				List<String> requiredProperties = getRequiredPropertyList(add);
				if(requiredProperties.isEmpty() || propertyList.containsAll(requiredProperties)) {
					if(!ret.contains(add)) {
						ret.add(add);
					}
				}
			}
		}
		return ret;
	}
	
	private static List<String> splitString(String toSplit) {
		List<String> ret = new ArrayList<String>();
		if(!toSplit.equals("")) {
			ret.addAll(Arrays.asList(toSplit.split(REG_EX)));
		}
		return ret;
	}
	
	private void combineList(List<String> a, List<String> b) {
		for(String s : b) {
			if(!a.contains(s)) {
				a.add(s);
			}
		}
	}
}
