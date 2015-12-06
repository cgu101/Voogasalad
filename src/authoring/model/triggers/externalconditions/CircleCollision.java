package authoring.model.triggers.externalconditions;

import authoring.model.actions.ActionTriggerHelper;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.externaltriggers.ATwoActorExternalTrigger;

public class CircleCollision extends ATwoActorExternalTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -2528189707062365870L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor a, Actor b) {
		Double distance = ActionTriggerHelper.distance(a, b);
		Double sizeA = a.getPropertyValue("size");
		Double sizeB = b.getPropertyValue("size");
		Double radiusSum = sizeA + sizeB;
		return Double.compare(distance, radiusSum) <= 0;
	}

}