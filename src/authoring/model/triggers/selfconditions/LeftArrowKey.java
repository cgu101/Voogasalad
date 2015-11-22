package authoring.model.triggers.selfconditions;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class LeftArrowKey extends ASelfTrigger {

	@Override
	public boolean condition(List<IAction> actions, ActorGroups actorGroup, InputManager inputManager,
			Actor... actors) {

		if (inputManager.getValue("LEFT")) {
			return true;
		}
		return false;
	}
}
