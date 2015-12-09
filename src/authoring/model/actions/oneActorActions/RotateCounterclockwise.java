package authoring.model.actions.oneActorActions;

import java.util.Map;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

public class RotateCounterclockwise extends AOneActorAction {

	@Override
	public void run(Parameters parameters, State state, Actor a) {
		Double rotation = 20.0;
		Property<Double> angle = (Property<Double>) a.getProperty("angle");
		angle.setValue((angle.getValue() + rotation) % 360);
	}
}
