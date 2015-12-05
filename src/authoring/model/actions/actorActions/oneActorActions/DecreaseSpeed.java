package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class DecreaseSpeed<V> extends AOneActorAction<V> {
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor a) {
		Double decrement = 0.5;

		Property<Double> speed = (Property<Double>) a.getProperty("speed");
		speed.setValue(speed.getValue() - decrement);
		
		double minSpeed = 0;
		if (speed.getValue() < minSpeed) {
			speed.setValue(minSpeed);
		}
	}
}
