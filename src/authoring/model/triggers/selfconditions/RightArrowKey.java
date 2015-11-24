package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class RightArrowKey extends ASelfTrigger {

	@Override
	public boolean condition(InputManager inputManager, Actor... actors) {

		if (inputManager.getValue("RIGHT")) {
			return true;
		}
		return false;
	}
}
