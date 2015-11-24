package authoring.model.triggers;

import authoring.model.actors.Actor;
import player.InputManager;

public interface ITriggerEvent {
	
	public boolean condition(InputManager inputManager, Actor... actors);

}
