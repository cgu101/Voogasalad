package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class MoveBackwards<V> extends AOneActorAction<V>{

	@SuppressWarnings("unchecked")
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor actor) {
		Double angle = ((Property<Double>) actor.getProperty("angle")).getValue();
		Double speed = ((Property<Double>) actor.getProperty("speed")).getValue();
		Double x = ((Property<Double>) actor.getProperty("xLocation")).getValue();
		Double y = ((Property<Double>) actor.getProperty("yLocation")).getValue();
		
		x = x - Math.cos(Math.toRadians(angle)) * speed; 
		y = y - Math.sin(Math.toRadians(angle)) * speed;

		((Property<Double>)actor.getProperty("xLocation")).setValue(x);
		((Property<Double>)actor.getProperty("yLocation")).setValue(y);
	}
}
