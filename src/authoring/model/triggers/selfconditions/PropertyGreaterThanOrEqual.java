package authoring.model.triggers.selfconditions;

import authoring.model.triggers.selftriggers.APropertyTest;

public class PropertyGreaterThanOrEqual extends APropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -6522407096096774660L;

	protected boolean checkCondition(Double property, Double value) {
		return Double.compare(property, value) >= 0;
	}
}
