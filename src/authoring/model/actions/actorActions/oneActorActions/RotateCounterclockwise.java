package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;

public class RotateCounterclockwise<V> extends AOneActorAction<V>{

	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor a) {
		Double rotation = 20.0;
		Property<Double> angle = (Property<Double>) a.getProperty("angle");
		angle.setValue((angle.getValue() + rotation) % 360);
	}
}
