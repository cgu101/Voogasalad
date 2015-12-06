package authoring.model.triggers.selftriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.ATriggerEvent;
import player.InputManager;

public abstract class AInputSelfTrigger extends ATriggerEvent {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 8676041929154500109L;

	@SuppressWarnings("rawtypes")
	public abstract boolean condition(Parameters parameters, InputManager inputManager, Actor actor);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor... actors) {
		return condition(parameters, inputManager, actors[0]);
	}
}
