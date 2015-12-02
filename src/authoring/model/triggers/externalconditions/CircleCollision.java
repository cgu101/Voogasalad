package authoring.model.triggers.externalconditions;

import authoring.model.actions.ActionTriggerHelper;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class CircleCollision extends ASelfTrigger {

	@SuppressWarnings("unchecked")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor... actors) {

		Actor actorA = actors[0];
		Actor actorB = actors[1];

		Double sizeA = ((Property<Double>) actorA.getProperties().getComponents().get("size")).getValue();
		Double sizeB = ((Property<Double>) actorB.getProperties().getComponents().get("size")).getValue();

		double radiusSum = sizeA + sizeB;
		double distance = ActionTriggerHelper.distance(actorA, actorB);

		if (Double.compare(distance, radiusSum) <= 0) {
			return true;
		}
		return false;
	}

}