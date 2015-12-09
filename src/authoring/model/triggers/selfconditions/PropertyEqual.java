package authoring.model.triggers.selfconditions;

import authoring.model.triggers.selftriggers.APropertyTest;

public abstract class PropertyEqual extends APropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 2066888921008110471L;

	protected boolean checkCondition(Double property, Double value) {
		return Double.compare(property, value) == 0;
	}
}
