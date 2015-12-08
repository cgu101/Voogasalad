package authoring.model.triggers.externalconditions;

import authoring.model.triggers.externaltriggers.ATwoPropertyTest;

public class PropertyGreaterThanProperty extends ATwoPropertyTest {
	/**
	 * Generated serial 
	 */
	private static final long serialVersionUID = 8162265715697975466L;

	protected boolean checkCondition(Double propertyA, Double propertyB) {
		return Double.compare(propertyA, propertyB) > 0;
	}
}
