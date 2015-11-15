package authoring.model.triggers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import authoring.model.actions.ActorGroups;
import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;

public abstract class ATriggerEvent implements ITriggerEvent {
	
	public abstract boolean condition(List<IAction> actions, Map<String, Bundle<Actor>> map, Actor... actors);

	protected boolean performActions(List<IAction> actions, Map<String, Bundle<Actor>> map, Actor... actors) {
		Iterator<IAction> iterator = actions.iterator();
		while (iterator.hasNext()) {
			IAction currentAction = iterator.next();
			for(Actor actor : actors){
				currentAction.run(new ActorGroups(), actor);
			}
		}
		return true;
	}
	
	@Override
	public String getUniqueID() {
		return getClass().getName();
	}
}
