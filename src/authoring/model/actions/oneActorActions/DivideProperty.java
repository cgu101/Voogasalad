package authoring.model.actions.oneActorActions;

import authoring.model.properties.Property;

public class DivideProperty extends AChangeProperty {
	@Override
	protected void ExecuteOperation(Double divisor, Property<Double> property) {
		property.setValue(property.getValue() / divisor);
	}
}
