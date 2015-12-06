package authoring.model.actions;

import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import engine.State;

public abstract class AActorActions<V> implements IAction<V> {
	
	@Override
	public final void run(Map<String, V> parameters_values, State state, Actor... a){
		ActorGroups actorGroups = state.getActorMap();
		run(parameters_values, actorGroups, a);
		actorGroups.addActor(a);
	}
	
	/**
	 * @param parameters_values Map of parameters and values
	 * @param actorGroups ActorGroups object
	 * @param a Actors that you want to run the action on/with
	 */	
	public abstract void run(Map<String, V> parameters_values, ActorGroups actorGroup, Actor... a);
}
