// This entire file is part of my masterpiece.
// Inan Tainwala
package authoring.model.actions.oneActorActions.propertyActions;

import authoring.model.actors.Actor;
import authoring.model.properties.Property;

public class MultiplyProperty extends AChangeProperty {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3335946607966237844L;

	@Override
	public void run(Property property, Object value, Actor a) {
		property.setValue((Double)property.getValue() * (Double)value); 
	}
}
