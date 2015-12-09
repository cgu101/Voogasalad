package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import engine.State;
import player.InputManager;

public class SetPropertyValue extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -8130811793608757750L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		String propetyName = (String) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0");
		Double newValue = (Double) parameters.getParameter(ParametersKey.PARAM_PREFIX + "1");
		actor.setProperty(propetyName, newValue);
	}
}
