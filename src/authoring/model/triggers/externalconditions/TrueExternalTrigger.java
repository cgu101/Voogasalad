package authoring.model.triggers.externalconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.externaltriggers.AExternalTrigger;
import player.InputManager;

public class TrueExternalTrigger extends AExternalTrigger {

	public TrueExternalTrigger() {
	}

	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor... actors) {
		return true;
	}
}
