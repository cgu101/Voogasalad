package authoring.model.properties;

import authoring.model.bundles.Identifiable;

public class Property<T> implements Identifiable {

	private T myValue;
	private String identifier;
	
	public Property(String identifier, T value) {
		this.identifier = identifier;
		this.myValue = value;
	}	
	public T getValue() {
		return myValue;
	}
	public void setValue(T value) {
		myValue = value;
	}
	
	@Override
	public String getUniqueID() {
		return identifier;
	}
	
	@Override
	public Identifiable getCopy() {
		Property<T> copy = new Property<T>(identifier, myValue);
		return copy;
	}
}
