package authoring.model.triggers.externalconditions;

import authoring.model.actions.ActionTriggerHelper;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.externaltriggers.ATwoActorExternalTrigger;

public class InRange extends ATwoActorExternalTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 5865539801335555374L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, Actor a, Actor b) {
		Double rangeA = a.getPropertyValue("range");
		Double sizeB = b.getPropertyValue("size");
		double distanceToActorB = ActionTriggerHelper.distance(a, b) - sizeB;
		return Double.compare(distanceToActorB, rangeA) <= 0;
	}

}