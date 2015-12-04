package resources.keys;

import java.util.ResourceBundle;

public class PropertyKeyResource {
	private final static ResourceBundle resources = ResourceBundle.getBundle("resources/PropertyKeys");
	public static String getKey (PropertyKey keyOfKey) {
		return resources.getString(keyOfKey.toString());
	}
}
