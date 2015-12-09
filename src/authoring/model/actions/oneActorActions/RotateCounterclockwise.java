package authoring.model.actions.oneActorActions;

import java.util.Map;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import engine.State;
import player.InputManager;

public class RotateCounterclockwise extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 1980998032836027794L;
	private static final String ANGLE = "angle";

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		Double rotation = 20.0;
		Property<Double> angle = (Property<Double>) actor.getProperty(ActorProperties.ANGLE.getKey());
		angle.setValue((angle.getValue() + rotation) % 360);
	}
}
