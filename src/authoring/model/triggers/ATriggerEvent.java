package authoring.model.triggers;

import java.util.Iterator;
import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import player.InputManager;

public abstract class ATriggerEvent implements ITriggerEvent {

	public abstract boolean condition(List<IAction> actions, ActorGroups actorGroup, InputManager inputManager,
			Actor... actors);

	public boolean performActions(List<IAction> actions, ActorGroups actorGroup, InputManager inputManager, Actor... actors) {
		if (condition(actions, actorGroup, inputManager, actors)) {
			Iterator<IAction> iterator = actions.iterator();
			while (iterator.hasNext()) {
				iterator.next().run(actorGroup, actors);
			}
			return true;
		}
		return false;
	}

}
