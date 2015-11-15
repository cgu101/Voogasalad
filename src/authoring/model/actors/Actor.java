package authoring.model.actors;

import authoring.model.bundles.Bundle;
import authoring.model.bundles.Identifiable;
import authoring.model.properties.Property;

public class Actor implements Identifiable, IActor {

	private Bundle<Property<?>> myPropertyBundle;
	private String identifier;

	public Actor(Bundle<Property<?>> myPropertyBundle, String identifier) {
		this.myPropertyBundle = myPropertyBundle;
		this.identifier = identifier;
	}

	@Override
	public Bundle<Property<?>> getProperties() {
		return myPropertyBundle;
	}

	@Override
	public String getUniqueID() {
		return identifier;
	}
}