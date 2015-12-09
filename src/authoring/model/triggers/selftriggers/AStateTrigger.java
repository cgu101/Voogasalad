package authoring.model.triggers.selftriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.ATriggerEvent;
import engine.State;
import player.InputManager;

public abstract class AStateTrigger extends ATriggerEvent {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 7331097274706841567L;

	@SuppressWarnings("rawtypes")
	public abstract boolean condition(Parameters parameters, State state, Actor actor);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, State state, Actor... actors) {
		return condition(parameters, state, actors[0]);
	}
}
