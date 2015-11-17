package authoring.model.actors;

import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;

public interface IActor {
	public Bundle<Property<?>> getProperties();
}