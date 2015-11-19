package authoring.model.actors;

import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;

public interface IActor {
	public Bundle<Property<?>> getProperties();
	public Property<?> getProperty(String identifier);
	void setProperty(String identifier, Object value);
	public <T> void setProperty(Property<T> property);
	<T> void setProperty(Property<T>... property);
}