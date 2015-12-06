package authoring.model.triggers.selfconditions;

public abstract class PropertyLessThanOrEqual extends APropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 8170266574784510102L;

	protected boolean checkCondition(int property, Double value) {
		return Double.compare(property, value) <= 0;
	}
}
