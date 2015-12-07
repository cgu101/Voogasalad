package authoring.model.actions.oneActorActions;

import authoring.model.actions.AChangeProperty;

public class DivideProperty extends AChangeProperty {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 4564193728400194606L;

	@Override
	protected Double calculateValue(Double value, Double divisor, Double minimum) {
		value /= divisor;
		return minimum != null && Double.compare(value, minimum) < 0 ? minimum : value;
	}
}
