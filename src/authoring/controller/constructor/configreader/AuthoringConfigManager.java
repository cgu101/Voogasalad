package authoring.controller.constructor.configreader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AuthoringConfigManager {
	
	private Map<String, Map<String, ResourceBundle>> bundleMaps;
	private ResourceBundle myConfiguration;
	
	private static final String CONFIGURATION_DIR = "authoring/files/%s";
	private static final String CONFIGURATION = "configuration";
	private static final String DIRECTORY_FORMAT = "%s/%s";
	private static final String REG_EX = ",";
	
	private static final String TYPE = "type";	
	private static final String GENERAL = "general";
	private static final String REQUIRED_PROPERTIES = "requiredProperties";
	
	private static final AuthoringConfigManager myManager = new AuthoringConfigManager();
	
	private AuthoringConfigManager() {
		load();
	}
	
	private void load() {
		myConfiguration = ResourceBundle.getBundle(String.format(CONFIGURATION_DIR, CONFIGURATION));
		bundleMaps = new HashMap<String, Map<String, ResourceBundle>>();
		for(String s : splitString(myConfiguration.getString(CONFIGURATION))) {
			bundleMaps.put(s, loadMap(s));
		}
	}
	
	private Map<String, ResourceBundle> loadMap(String type) {	
		Map<String, ResourceBundle> myMap = new HashMap<String, ResourceBundle>();
		List<String> toAdd = splitString(myConfiguration.getString(type));
		for(String s: toAdd) {
			ResourceBundle r = ResourceBundle.getBundle(String.format(CONFIGURATION_DIR, String.format(DIRECTORY_FORMAT, type, s)));
			myMap.put(s, r);
		}
		return myMap;
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
	 * 
	 * 
	 */
	
	public List<String> getKeyList(String type) {
		return new ArrayList<String>(bundleMaps.get(type).keySet());
	}
	
	/**
	 * 
	 */
	
	public String getTypeInfo(String type, String instance, String info) {
		return bundleMaps.get(type).get(instance).getString(info);
	}
	
	/**
	 * This method will return a String array of all default Properties. 
	 * 
	 * @return List<String> 
	 */	
	public List<String> getPropertyList(String actor) {
		return splitString(bundleMaps.get(ResourceType.ACTORS)
				.get(actor)
				.getString(ResourceType.PROPERTIES));
	}

	/**
	 * This method will return the default value for a property mapped to an actor.
	 * 
	 * @param actor
	 * @param property
	 * @return String
	 */
	public String getDefaultPropertyValue(String actor, String property) {
		return bundleMaps.get(ResourceType.ACTORS)
				.get(actor)
				.getString(property);
	}
	
	/**
	 * This method will return the type for the given property. These types are primitives and are used
	 * for the instantiation of the property class. 
	 * 
	 * @param property
	 * @return String
	 */
	public String getPropertyType(String property) {
		return bundleMaps.get(ResourceType.ACTORS)
				.get(property)
				.getString(TYPE);
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
		List<String> actorActionList = splitString(bundleMaps.get(ResourceType.ACTORS).get(actor).getString(type));		
		List<String> generalActionList = splitString(myConfiguration.getString(String.format("%s.%s", type, GENERAL)));		
		return combineLists(actorActionList, generalActionList);
	}
	
	/**
	 * This method will return the properties required by an trigger or action.
	 * 
	 * @param instance
	 * @return List<String>
	 */
	public List<String> getRequiredPropertyList(String type, String instance) {
		return splitString(bundleMaps.get(type).get(instance).getString(REQUIRED_PROPERTIES));
	}
	
	
	private static List<String> splitString(String toSplit) {
		List<String> ret = new ArrayList<String>();
		if(!toSplit.equals("")) {
			ret.addAll(Arrays.asList(toSplit.split(REG_EX)));
		}
		return ret;
	}
	
	
	@SafeVarargs
	private static List<String> combineLists(List<String>...lists) {
		Set<String> container = new HashSet<String>();
		for(List<String> list : lists) {
			container.addAll(list);
		}
		
		List<String> ret = new ArrayList<String>();
		ret.addAll(container);
		return ret;
	}

	
//	/**
//	 * This method will return a String array of all default Actors. 
//	 * 
//	 * @return List<String> 
//	 */
//	public List<String> getActorList() {
//		return new ArrayList<String>(actorMap.keySet());
//	}
//	
//	/**
//	 * This method will return a String array of all default Properties. 
//	 * 
//	 * @return List<String> 
//	 */	
//	public List<String> getPropertyList() {
//		return new ArrayList<String>(propertyMap.keySet());
//	}
}
