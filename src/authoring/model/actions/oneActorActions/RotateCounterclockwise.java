package authoring.model.actions.oneActorActions;

import java.util.Map;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

public class RotateCounterclockwise extends AOneActorAction {

	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		Double rotation = 20.0;
		Property<Double> angle = (Property<Double>) actor.getProperty(ActorProperties.ANGLE.getKey());
		angle.setValue((angle.getValue() + rotation) % 360);
	}
}
