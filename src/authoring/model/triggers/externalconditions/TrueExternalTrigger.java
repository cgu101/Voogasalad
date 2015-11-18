package authoring.model.triggers.externalconditions;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.triggers.externaltriggers.AExternalTrigger;
import player.InputManager;

public class TrueExternalTrigger extends AExternalTrigger {

	public TrueExternalTrigger() {
	}

	@Override
	public boolean condition(List<IAction> actions, ActorGroups actorGroup, InputManager inputManager, Actor... actors) {
		return true;
	}
}
