package authoring.model.triggers.externalconditions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.triggers.externaltriggers.AExternalTrigger;
import player.InputManager;

public class TrueExternalTrigger extends AExternalTrigger {

	public TrueExternalTrigger() {
	}

	@Override
	public boolean condition(ActorGroups actorGroup, InputManager inputManager, Actor... actors) {
		return true;
	}
}
