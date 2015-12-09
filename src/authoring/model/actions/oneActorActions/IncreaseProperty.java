package authoring.model.actions.oneActorActions;

import authoring.model.actions.AChangeProperty;

public class IncreaseProperty extends AChangeProperty {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3612830585385260543L;

	@Override
	protected Double calculateValue(Double value, Double increment, Double maximum) {
		value += increment;
		double val = Double.compare(value, maximum) > 0 ? maximum : value;
		return val;
//		return maximum != null && Double.compare(value, maximum) > 0 ? maximum : value;
	}
}
