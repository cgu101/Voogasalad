package authoring.model.triggers.selfconditions;

public abstract class PropertyLessThan extends APropertyTest {
	/**
	 * Generated serial version
	 */
	private static final long serialVersionUID = -7330673705733755062L;

	protected boolean checkCondition(int property, Double value) {
		return Double.compare(property, value) < 0;
	}
}
