package authoring.model.triggers;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import player.InputManager;

public interface ITriggerEvent {

	public boolean performActions(List<IAction> actions, ActorGroups actorGroup, InputManager inputManager, Actor... actors);

}
