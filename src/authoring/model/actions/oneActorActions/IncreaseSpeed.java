package authoring.model.actions.oneActorActions;

import java.util.Map;
import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

public class IncreaseSpeed extends AOneActorAction {
	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		Double increment = 1.5;

		Property<Double> speed = (Property<Double>) actor.getProperty("speed");
		speed.setValue(speed.getValue() + increment);
		
		double maxSpeed = 30;
		if (speed.getValue() > maxSpeed) {
			speed.setValue(maxSpeed);
		}
	}
}
