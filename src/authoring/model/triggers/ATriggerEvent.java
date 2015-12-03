package authoring.model.triggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import player.InputManager;

public abstract class ATriggerEvent implements ITriggerEvent {

	public abstract boolean condition(Parameters parameters, InputManager inputManager, Actor... actors);

}
