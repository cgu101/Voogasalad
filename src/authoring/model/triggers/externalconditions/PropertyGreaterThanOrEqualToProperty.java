package authoring.model.triggers.externalconditions;

import authoring.model.triggers.externaltriggers.ATwoPropertyTest;

public class PropertyGreaterThanOrEqualToProperty extends ATwoPropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 1481971995138107317L;

	protected boolean checkCondition(Double propertyA, Double propertyB) {
		return Double.compare(propertyA, propertyB) >= 0;
	}
}
