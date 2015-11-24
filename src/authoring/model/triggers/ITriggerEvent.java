package authoring.model.triggers;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import player.InputManager;

public interface ITriggerEvent {
	
	public boolean condition(ActorGroups actorGroup, InputManager inputManager, Actor... actors);

}
