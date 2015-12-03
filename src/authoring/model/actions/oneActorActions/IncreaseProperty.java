package authoring.model.actions.oneActorActions;

import authoring.model.properties.Property;

public class IncreaseProperty extends AChangeProperty {
	@Override
	protected void ExecuteOperation(Double increment, Property<Double> property) {
		property.setValue(property.getValue() + increment);
	}
}
