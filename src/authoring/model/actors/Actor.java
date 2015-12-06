package authoring.model.actors;

import java.io.Serializable;

import authoring.model.bundles.Bundle;
import authoring.model.bundles.Identifiable;
import authoring.model.properties.Property;

public class Actor implements Identifiable, IActor, Serializable {

	private Bundle<Property<?>> myPropertyBundle;
	private String identifier;

	public Actor(Bundle<Property<?>> myPropertyBundle, String identifier) {
		this.myPropertyBundle = myPropertyBundle;
		this.identifier = identifier;
	}

	public Actor (Actor a) {
		this.myPropertyBundle = new Bundle<Property<?>>(a.getProperties());
		this.identifier = a.getUniqueID();
	}

	@Override
	public Bundle<Property<?>> getProperties() {
		return myPropertyBundle;
	}

	@Override
	public String getUniqueID() {
		return identifier;
	}

	@Override
	public Identifiable getCopy() {
		return new Actor(this);
	}

	@Override
	public Property<?> getProperty(String identifier) {
		return (Property<?>) myPropertyBundle.getComponents().get(identifier);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getPropertyValue(String identifier) {
		return (T) myPropertyBundle.getComponents().get(identifier).getValue();
	}

	@Override
	public void setProperty(String identifier, Object value) {
		myPropertyBundle.getComponents().get(identifier).setValue(value);
	}
	
	@Override
	public <T> void setProperty(Property<T> property) {
		myPropertyBundle.getComponents().put(property.getUniqueID(), property);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void setProperty(Property<T>... properties) {
		for(Property<T> prop: properties){
			setProperty(prop);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getGroupName() {
		return ((Property<String>) myPropertyBundle.getComponents().get("groupID")).getValue();
	}
	
	public boolean hasProperty(String identifier) {
		return getProperties().getComponents().containsKey(identifier);
	}
}