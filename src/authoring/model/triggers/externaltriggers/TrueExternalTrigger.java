package authoring.model.triggers.externaltriggers;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;

public class TrueExternalTrigger extends AExternalTrigger {

	public TrueExternalTrigger(Bundle<IAction> actions, List<Actor> actors) {
		super(actions, actors);
	}

	@Override
	public boolean condition() {
		return performActions();
	}

}
