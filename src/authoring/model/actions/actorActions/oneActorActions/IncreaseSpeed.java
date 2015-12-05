package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class IncreaseSpeed<V> extends AOneActorAction<V> {
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor a) {
		Double increment = 1.5;

		Property<Double> speed = (Property<Double>) a.getProperty("speed");
		speed.setValue(speed.getValue() + increment);
		
		double minSpeed = 0;
		if (speed.getValue() < minSpeed) {
			speed.setValue(minSpeed);
		}
	}
}
