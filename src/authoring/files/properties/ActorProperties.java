package authoring.files.properties;

import java.util.ResourceBundle;

public enum ActorProperties {
	ANGLE("ANGLE"),
	DISTANCE_TRAVELED("DISTANCE_TRAVELED"),
	GROUP_ID("GROUP_ID"),
	IMAGE("IMAGE"),
	HEALTH("HEALTH"),
	RANGE("RANGE"),
	SIZE("SIZE"),
	SPEED("SPEED"),
	X_LOCATION("X_LOCATION"),
	Y_LOCATION("Y_LOCATION");


	private final ResourceBundle resources = ResourceBundle.getBundle("authoring/files/properties/ActorProperties");
	private String key;

	private ActorProperties(String s){
		key = resources.getString(s);
	}

	public String getKey(){
		return key;
	}
}
