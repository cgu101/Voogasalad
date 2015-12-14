// This entire file is part of my masterpiece.
// Inan Tainwala
package authoring.model.actions.oneActorActions.propertyActions;

import authoring.model.actors.Actor;
import authoring.model.properties.Property;

public class IncreaseProperty extends AChangeProperty {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3612830585385260543L;

	@Override
	public void run(Property property, Object value, Actor a) {
		property.setValue((Double)property.getValue() + (Double)value); 
	}
}
