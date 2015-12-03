package authoring.model.actions.oneActorActions;

import authoring.model.properties.Property;

public class DecreaseProperty extends AChangeProperty {
	@Override
	protected void ExecuteOperation(Double decrement, Property<Double> property) {
		property.setValue(property.getValue() - decrement);
	}
}
