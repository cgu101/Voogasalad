package authoring.model.triggers.externalconditions;

import authoring.model.triggers.externaltriggers.ATwoPropertyTest;

public abstract class PropertyLessThanOrEqualToProperty extends ATwoPropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 5002861103643774395L;

	protected boolean checkCondition(Double propertyA, Double propertyB) {
		return Double.compare(propertyA, propertyB) <= 0;
	}
}
