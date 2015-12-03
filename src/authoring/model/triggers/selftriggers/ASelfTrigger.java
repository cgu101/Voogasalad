package authoring.model.triggers.selftriggers;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.ATriggerEvent;
import player.InputManager;

public abstract class ASelfTrigger extends ATriggerEvent {
	private static final int FIRST_ACTOR_INDEX = 0;
	
	public abstract boolean condition(Parameters parameters, InputManager inputManager, Actor actor);
	
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor... actors) {
		return condition(parameters, inputManager, actors[FIRST_ACTOR_INDEX]);
	}
}
