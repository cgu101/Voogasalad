package authoring.model.triggers.externaltriggers;

import java.util.List;

import authoring.model.actions.ActorGroups;
import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import player.InputManager;

public class TrueExternalTrigger extends AExternalTrigger {

	public TrueExternalTrigger() {
	}

	@Override
	public boolean condition(List<IAction> actions, ActorGroups actorGroup, InputManager inputManager, Actor... actors) {
		return performActions(actions, actorGroup, actors);
	}
}
