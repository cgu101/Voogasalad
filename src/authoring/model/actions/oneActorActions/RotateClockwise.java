package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import engine.State;
import player.InputManager;

public class RotateClockwise extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -401773162575161667L;
	private static final String ANGLE = "angle";

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		Double rotation = (Double) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0");
		Double angle = actor.getPropertyValue(ANGLE);
		actor.setProperty(ANGLE, (angle + rotation) % 360);	
	}
}
