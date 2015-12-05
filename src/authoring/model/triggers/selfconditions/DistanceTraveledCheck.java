package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class DistanceTraveledCheck extends ASelfTrigger {
	public DistanceTraveledCheck() {
	}

	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor actor) {
		Double distanceTraveled = actor.getPropertyValue("distanceTraveled");
		double maxDistance = 400;
		return (Double.compare(distanceTraveled, maxDistance) >= 0);
	}
}
