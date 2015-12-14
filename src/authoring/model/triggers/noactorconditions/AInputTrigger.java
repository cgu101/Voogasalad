package authoring.model.triggers.noactorconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.ATriggerEvent;
import engine.State;
import player.InputManager;

public abstract class AInputTrigger extends ATriggerEvent {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 8676041929154500109L;

	@SuppressWarnings("rawtypes")
	public abstract boolean condition(Parameters parameters, InputManager inputManager);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, State state, Actor... actors) {
		return condition(parameters, inputManager);
	}
}
