package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.AActorSelfTrigger;

public abstract class PropertyTest extends AActorSelfTrigger {
	// TODO Fix hard-coded values
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
