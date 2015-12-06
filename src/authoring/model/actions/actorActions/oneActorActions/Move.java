package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

/**
 * @author Inan
 *
 */
public class Move<V> extends AOneActorAction<V> {
	@SuppressWarnings("unchecked")
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor actor) {
		Double angle = ((Property<Double>) actor.getProperty("angle")).getValue();
		Double speed = ((Property<Double>) actor.getProperty("speed")).getValue();
		Double x = ((Property<Double>) actor.getProperty("xLocation")).getValue();
		Double y = ((Property<Double>) actor.getProperty("yLocation")).getValue();

		x = x + Math.cos(Math.toRadians(angle)) * speed;
		y = y + Math.sin(Math.toRadians(angle)) * speed;

		if (actor.hasProperty("distanceTraveled")) {
			actor.setProperty("distanceTraveled", (Double) actor.getPropertyValue("distanceTraveled") + speed);
		}

		if (true) {	//TODO: Check if Wrap around functionality is enabled
			x = checkBoundary(x, 1100 - 400);
			y = checkBoundary(y, 600);
		}

		actor.setProperty("xLocation", x);
		actor.setProperty("yLocation", y);
	}

	private double checkBoundary(double coordinate, int bound) {
		if (coordinate < 0 || coordinate > bound)
			return (coordinate + bound) % bound;
		return coordinate;
	}
}
