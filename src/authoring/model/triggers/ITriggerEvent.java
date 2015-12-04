package authoring.model.triggers;

import java.io.Serializable;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import player.InputManager;

public interface ITriggerEvent extends Serializable {
	
	public boolean condition(Parameters parameters, InputManager inputManager, Actor... actors);

}
