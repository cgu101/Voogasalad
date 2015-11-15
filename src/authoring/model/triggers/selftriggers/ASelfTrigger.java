package authoring.model.triggers.selftriggers;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.triggers.ATriggerEvent;

public abstract class ASelfTrigger extends ATriggerEvent {

	protected ASelfTrigger(Bundle<IAction> actions, List<Actor> actors) {
		super(actions, actors);
	}

}
