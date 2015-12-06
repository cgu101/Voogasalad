package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.AActorSelfTrigger;
import authoring.model.triggers.selftriggers.AInputSelfTrigger;
import player.InputManager;

public class LeftArrowKey extends AInputSelfTrigger {

	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor actor) {

		if (inputManager.getValue("LEFT")) {
			return true;
		}
		return false;
	}
}
