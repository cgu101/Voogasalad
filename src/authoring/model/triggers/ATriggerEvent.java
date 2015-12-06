package authoring.model.triggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import player.InputManager;

public abstract class ATriggerEvent implements ITriggerEvent {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 143065301018200501L;

	@SuppressWarnings("rawtypes")
	public abstract boolean condition(Parameters parameters, InputManager inputManager, Actor... actors);
}
