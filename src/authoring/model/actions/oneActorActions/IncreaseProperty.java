package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionProperty;
import authoring.model.properties.Property;

public class IncreaseProperty extends AActionProperty {
	@Override
	protected void ExecuteOperation(Double increment, Property<Double> property) {
		property.setValue(property.getValue() + increment);
	}
}
