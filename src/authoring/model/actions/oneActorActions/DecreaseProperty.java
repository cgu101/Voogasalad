package authoring.model.actions.oneActorActions;

import authoring.model.actions.AChangeProperty;

public class DecreaseProperty extends AChangeProperty {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 4732187169635130042L;

	@Override
	protected Double calculateValue(Double value, Double decrement, Double minimum) {
		value -= decrement;
		return minimum != null && Double.compare(value, minimum) < 0 ? minimum : value;
	}
}
