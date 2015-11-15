package authoring.model.triggers;

import java.util.List;

import authoring.model.actions.ActorGroups;
import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Identifiable;
import player.InputManager;

public interface ITriggerEvent extends Identifiable {

	public abstract boolean condition(List<IAction> actions, ActorGroups actorGroup, InputManager inputManager, Actor... actors);

}
