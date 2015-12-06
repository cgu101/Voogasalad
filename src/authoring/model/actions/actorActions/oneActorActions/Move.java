package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class Move<V> extends AOneActorAction<V>{

	@SuppressWarnings("unchecked")
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor actor) {
		Double angle = ((Property<Double>) actor.getProperty(ActorProperties.ANGLE.getKey())).getValue();
		Double speed = ((Property<Double>) actor.getProperty(ActorProperties.SPEED.getKey())).getValue();
		Double x = ((Property<Double>) actor.getProperty(ActorProperties.X_LOCATION.getKey())).getValue();
		Double y = ((Property<Double>) actor.getProperty(ActorProperties.Y_LOCATION.getKey())).getValue();
		
		x = x + Math.cos(Math.toRadians(angle)) * speed; 
		y = y + Math.sin(Math.toRadians(angle)) * speed;

		if (actor.hasProperty(ActorProperties.DISTANCE_TRAVELED.getKey())) {
			//		TODO REFACTOR....
			actor.setProperty("distanceTraveled", (Double) actor.getPropertyValue("distanceTraveled") + speed);
		}
		
//		TODO UPDATE
		x = checkBoundary(x, 1100-400);
		y = checkBoundary(y, 600);

		((Property<Double>)actor.getProperty(ActorProperties.X_LOCATION.getKey())).setValue(x);
		((Property<Double>)actor.getProperty(ActorProperties.Y_LOCATION.getKey())).setValue(y);		
	}
	
	private double checkBoundary(double coordinate, int bound) {
		if (coordinate < 0 || coordinate > bound)
			return (coordinate+bound) % bound;
		return coordinate;
	}
}
