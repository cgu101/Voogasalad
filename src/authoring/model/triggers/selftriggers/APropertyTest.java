package authoring.model.triggers.selftriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;

public abstract class APropertyTest extends AActorSelfTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 1622215949688922117L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor actor) {
		String propertyName = (String) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0");
		Double property = actor.getPropertyValue(propertyName);
		Double value = (Double) parameters.getParameter(ParametersKey.PARAM_PREFIX + "1");
		return actor.hasProperty(propertyName) ? checkCondition(property, value) : false;
	}

	protected abstract boolean checkCondition(Double property, Double value);
}
