package authoring.model.triggers;

import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import player.InputManager;

public abstract class ATriggerEvent implements ITriggerEvent {

	public abstract boolean condition(Parameters parameters, InputManager inputManager, Actor... actors);

	@SuppressWarnings("unchecked")
	protected double distance(Actor actorA, Actor actorB) {
		Double xA = ((Property<Double>) actorA.getProperties().getComponents().get("xLocation")).getValue();
		Double yA = ((Property<Double>) actorA.getProperties().getComponents().get("yLocation")).getValue();
		Double xB = ((Property<Double>) actorB.getProperties().getComponents().get("xLocation")).getValue();
		Double yB = ((Property<Double>) actorB.getProperties().getComponents().get("yLocation")).getValue();
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}

}
