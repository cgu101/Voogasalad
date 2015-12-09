package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import authoring.model.triggers.selftriggers.AInputSelfTrigger;
import player.InputManager;

public class KeyPress extends AInputSelfTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -84412559723661948L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor actor) {
		return inputManager.getValue((String) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0"));
	}
}
