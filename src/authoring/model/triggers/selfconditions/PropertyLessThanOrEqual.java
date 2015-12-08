package authoring.model.triggers.selfconditions;

import authoring.model.triggers.selftriggers.APropertyTest;

public abstract class PropertyLessThanOrEqual extends APropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 8170266574784510102L;

	protected boolean checkCondition(Double property, Double value) {
		return Double.compare(property, value) <= 0;
	}
}
