package authoring.model.actions.parameterActions;

import authoring.model.actions.AParameterAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;

public class MultiplyProperty<V> extends AParameterAction<V>  {

	@Override
	public void run(Property<?> property, V value, Actor a) {
		Double mutilier = (Double) value;
		property.setValue((Double)property.getValue() * mutilier);
	}
}
