package authoring.model.actors;

import authoring.model.bundles.Bundle;
import authoring.model.bundles.Identifiable;
import authoring.model.properties.Property;

public class Actor implements Identifiable, IActor {

	private Bundle<Property<?>> myPropertyBundle = new Bundle<Property<?>>();

	public Actor() {
		// load properties from config
		Property<Integer> toAdd1 = new Property<Integer>("health", 10);
		myPropertyBundle.add(toAdd1);
	}

	@Override
	public Bundle<Property<?>> getProperties() {
		return myPropertyBundle;
	}

	@Override
	public String getUniqueID() {
		return this.getClass().getName();
	}

	@Override
	public void addProperty(Property<?> property) {
		myPropertyBundle.add(property);
	}

	@Override
	public void removeProperty(String propertyName) {
		myPropertyBundle.remove(propertyName);
	}
}