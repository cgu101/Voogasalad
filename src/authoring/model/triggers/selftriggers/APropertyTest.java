package authoring.model.triggers.selftriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;

public abstract class APropertyTest extends AActorSelfTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 1622215949688922117L;

	// TODO Fix hard-coded strings
	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor actor) {
		String propertyName = (String) parameters.getParameter("property");
		Double property = actor.getPropertyValue(propertyName);
		Double value = (Double) parameters.getParameter("value");
		return actor.hasProperty(propertyName) ? checkCondition(property, value) : false;
	}

	protected abstract boolean checkCondition(Double property, Double value);
}
