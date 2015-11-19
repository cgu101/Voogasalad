package authoring.model.actors;

import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;

public interface IActor {
	/**
	 * @return
	 */
	public Bundle<Property<?>> getProperties();
	
	/**
	 * @param identifier Identifier of the actor property that you want
	 * @return Property<?> with the specified identifier
	 */
	public Property<?> getProperty(String identifier);
	
	/**
	 * @param property Property that you want to add to the actor
	 */
	public <T> void setProperty(Property<T> property);
	
	/**
	 * @param property Properties that you want to add to the actor
	 */
	<T> void setProperty(Property<T>... property);
}