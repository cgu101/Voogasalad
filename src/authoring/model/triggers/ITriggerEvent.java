package authoring.model.triggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import player.InputManager;

public interface ITriggerEvent {
	
	public boolean condition(Parameters parameters, InputManager inputManager, Actor... actors);

}
