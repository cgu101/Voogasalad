package authoring.model.triggers.externaltriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.ATriggerEvent;
import player.InputManager;

public abstract class AExternalTrigger extends ATriggerEvent {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 4100817942508845434L;

	@SuppressWarnings("rawtypes")
	public abstract boolean condition(Parameters parameters, Actor... actors);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor... actors) {
		return condition(parameters, actors);
	}
}
