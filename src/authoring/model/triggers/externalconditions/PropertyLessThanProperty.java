package authoring.model.triggers.externalconditions;

import authoring.model.triggers.externaltriggers.ATwoPropertyTest;

public abstract class PropertyLessThanProperty extends ATwoPropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -4831908492649290690L;

	protected boolean checkCondition(Double propertyA, Double propertyB) {
		return Double.compare(propertyA, propertyB) < 0;
	}
}
