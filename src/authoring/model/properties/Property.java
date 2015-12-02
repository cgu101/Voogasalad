package authoring.model.properties;

import authoring.model.bundles.Identifiable;

/**
 * @author Inan
 *
 * @param <T> Generic property values (either String or Double. Can be states in the future)
 */
public class Property<T> implements Identifiable, IProperty<T> {

	private T myValue;
	private String identifier;

	public Property(String identifier, T value) {
		this.identifier = identifier;
		this.myValue = value;
	}

	public T getValue() {
		return myValue;
	}

	@SuppressWarnings("unchecked")
	public void setValue(Object value) {
		myValue = (T) value;
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
