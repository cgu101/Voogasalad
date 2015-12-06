package authoring.model.actions.oneActorActions;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

public class Move extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 2860378104198650581L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		Double angle = actor.getPropertyValue(ActorProperties.ANGLE.getKey());
		Double speed = actor.getPropertyValue(ActorProperties.SPEED.getKey());
		Double x = actor.getPropertyValue(ActorProperties.X_LOCATION.getKey());
		Double y = actor.getPropertyValue(ActorProperties.Y_LOCATION.getKey());
		
		x = x + Math.cos(Math.toRadians(angle)) * speed; 
		y = y + Math.sin(Math.toRadians(angle)) * speed;

		if (actor.hasProperty(ActorProperties.DISTANCE_TRAVELED.getKey())) {
			//		TODO REFACTOR....
			actor.setProperty("distanceTraveled", (Double) actor.getPropertyValue("distanceTraveled") + speed);
		}
		
//		TODO UPDATE
		x = checkBoundary(x, 1100-400);
		y = checkBoundary(y, 528);

		actor.setProperty(ActorProperties.X_LOCATION.getKey(), x);
		actor.setProperty(ActorProperties.Y_LOCATION.getKey(), y);
	}

	private double checkBoundary(double coordinate, int bound) {
		if (coordinate < 0 || coordinate > bound)
			return (coordinate + bound) % bound;
		return coordinate;
	}
}
