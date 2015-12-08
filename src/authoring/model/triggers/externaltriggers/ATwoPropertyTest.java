package authoring.model.triggers.externaltriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;

public abstract class ATwoPropertyTest extends ATwoActorExternalTrigger {
	/**
	 * Generate serial version ID
	 */
	private static final long serialVersionUID = -4018489398167728137L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor a, Actor b) {
		String propertyA = (String) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0");
		String propertyB = (String) parameters.getParameter(ParametersKey.PARAM_PREFIX + "1");
		return a.hasProperty(propertyA) && b.hasProperty(propertyB)
				? checkCondition(a.getPropertyValue(propertyA), b.getPropertyValue(propertyB)) : false;
	}

	protected abstract boolean checkCondition(Double propertyA, Double propertyB);
}
