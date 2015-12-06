package authoring.model.triggers.selfconditions;

public abstract class PropertyEqual extends APropertyTest {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 2066888921008110471L;

	protected boolean checkCondition(int property, Double value) {
		return Double.compare(property, value) == 0;
	}
}
