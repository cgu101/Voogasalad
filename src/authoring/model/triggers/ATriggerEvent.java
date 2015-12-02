package authoring.model.triggers;

import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import player.InputManager;

public abstract class ATriggerEvent implements ITriggerEvent {

	public abstract boolean condition(InputManager inputManager, Actor... actors);

}
