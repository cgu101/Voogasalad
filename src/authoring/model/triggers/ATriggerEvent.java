package authoring.model.triggers;

import java.util.Iterator;
import java.util.List;

import authoring.model.actions.ActorGroups;
import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;

public abstract class ATriggerEvent implements ITriggerEvent {

	private Bundle<IAction> actions;
	private List<Actor> actors;

	protected ATriggerEvent(Bundle<IAction> actions, List<Actor> actors) {
		this.actions = actions;
		this.actors = actors;
	}

	@Override
	public String getUniqueID() {
		return getClass().getName();
	}

	protected boolean performActions() {
		Iterator<IAction> iterator = actions.iterator();
		while(iterator.hasNext()) {
			IAction currentAction = iterator.next();
			for(Actor actor : actors){
				currentAction.run(new ActorGroups(), actor);
			}
		}
		return true;
	}
}
