package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import player.InputManager;

public class KeyPress extends ASelfTrigger {
	private static final int KEY_INDEX = 0;
//TODO FIX
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor actor) {
//		return inputManager.getValue((String) parameters.getParameterList("string").get(KEY_INDEX));
		return false;
	}
}
