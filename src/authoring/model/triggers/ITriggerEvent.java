package authoring.model.triggers;

import java.io.Serializable;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

public interface ITriggerEvent extends Serializable {
	@SuppressWarnings("rawtypes")
	public boolean condition(Parameters parameters, InputManager inputManager, State state, Actor... actors);
}
