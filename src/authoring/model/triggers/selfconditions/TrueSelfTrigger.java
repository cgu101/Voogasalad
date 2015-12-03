package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class TrueSelfTrigger extends ASelfTrigger {
	public TrueSelfTrigger() {
	}

	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor actor) {
		return true;
	}
}
