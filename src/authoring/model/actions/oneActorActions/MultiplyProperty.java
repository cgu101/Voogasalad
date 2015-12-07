package authoring.model.actions.oneActorActions;

import authoring.model.actions.AChangeProperty;

public class MultiplyProperty extends AChangeProperty {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3335946607966237844L;

	@Override
	protected Double calculateValue(Double value, Double factor, Double maximum) {
		value *= factor;
		return maximum != null && Double.compare(value, maximum) > 0 ? maximum : value;
	}
}
