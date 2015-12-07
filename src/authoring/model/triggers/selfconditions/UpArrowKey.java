package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.AActorSelfTrigger;
import authoring.model.triggers.selftriggers.AInputSelfTrigger;
import player.InputManager;

public class UpArrowKey extends AInputSelfTrigger {

	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor actor) {

		if (inputManager.getValue("UP")) {
			return true;
		}
		return false;
	}
}
