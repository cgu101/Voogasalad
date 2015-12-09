package authoring.model.actors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorPropertyMap implements Serializable {
	
	private static final long serialVersionUID = -3183358046621258403L;
	
	private Map<String, String> propertyMap;

	public ActorPropertyMap() {
		this.propertyMap = new HashMap<String, String>();
	}
	
	public List<String> getPropertyList() {
		return new ArrayList<>(propertyMap.keySet());
	}
	
	public String getPropertyValue(String property) {
		return propertyMap.get(property);
	}
	
	public void addProperty(String property, String value) {
		propertyMap.put(property, value);
	}
}
