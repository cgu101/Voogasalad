package authoring.model.triggers.externalconditions;

import authoring.model.triggers.externaltriggers.ATwoPropertyTest;

public abstract class PropertyEqualToProperty extends ATwoPropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3368433229332999278L;

	protected boolean checkCondition(Double propertyA, Double propertyB) {
		return Double.compare(propertyA, propertyB) == 0;
	}
}
