package authoring.model.triggers.selftriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.ATriggerEvent;
import engine.State;
import player.InputManager;

public abstract class AActorSelfTrigger extends ATriggerEvent {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 8384807132239670679L;

	@SuppressWarnings("rawtypes")
	public abstract boolean condition(Parameters parameters, Actor actor);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, State state, Actor... actors) {
		return condition(parameters, actors[0]);
	}
}
