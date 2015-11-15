package authoring.model.triggers.selftriggers;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;

public class TrueTrigger extends ASelfTrigger {
		
	public TrueTrigger(Bundle<IAction> actions, List<Actor> actors) {
		super(actions, actors);
	}
		
	@Override
	public boolean condition() {
		return performActions();
	}
}
