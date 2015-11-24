package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class LeftArrowKey extends ASelfTrigger {

	@Override
	public boolean condition(ActorGroups actorGroup, InputManager inputManager, Actor... actors) {

		if (inputManager.getValue("LEFT")) {
			return true;
		}
		return false;
	}
}
