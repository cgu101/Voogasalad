package authoring.model.triggers.externalconditions;

import authoring.files.properties.ActorProperties;
import authoring.model.ActionTriggerHelper;
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
		Double rangeA = a.getPropertyValue(ActorProperties.RANGE.getKey());
		Double sizeB = b.getPropertyValue(ActorProperties.SIZE.getKey());
		Double distanceToActorB = ActionTriggerHelper.distance(a, b) - sizeB;
		return Double.compare(distanceToActorB, rangeA) <= 0;
	}
}