package authoring.model.triggers.selfconditions;

import authoring.model.triggers.selftriggers.APropertyTest;

public class PropertyGreaterThan extends APropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 4210872700351308587L;

	protected boolean checkCondition(Double property, Double value) {
		return Double.compare(property, value) > 0;
	}
}
