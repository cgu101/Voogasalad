package authoring.model.triggers.externalconditions;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class CircleCollision extends ASelfTrigger {

	@SuppressWarnings("unchecked")
	@Override
	public boolean condition(List<IAction> actions, ActorGroups actorGroup, InputManager inputManager,
			Actor... actors) {

		Actor actorA = actors[0];
		Actor actorB = actors[1];
		
		Double sizeA = ((Property<Double>) actorA.getProperties().getComponents().get("size")).getValue();
		Double sizeB = ((Property<Double>) actorB.getProperties().getComponents().get("size")).getValue();

		double radiusSum = sizeA + sizeB;
		double distance = distance(actorA, actorB);
		
		if (Double.compare(distance, radiusSum) <= 0) {
			System.out.println("BOOOOOOOM!");
			return performActions(actions, actorGroup, actors);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public double distance(Actor actorA, Actor actorB) {
		Double xA = ((Property<Double>) actorA.getProperties().getComponents().get("xLocation")).getValue();
		Double yA = ((Property<Double>) actorA.getProperties().getComponents().get("yLocation")).getValue();
		Double xB = ((Property<Double>) actorB.getProperties().getComponents().get("xLocation")).getValue();
		Double yB = ((Property<Double>) actorB.getProperties().getComponents().get("yLocation")).getValue();
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}

}