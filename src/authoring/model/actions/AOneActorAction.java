package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

public abstract class AOneActorAction implements IAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -8958526881705467391L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor... actors) {
		run(parameters, state, actors[0]);
		state.getActorMap().addActor(actors[0]);
	}
	
	/**
	 * @param parameters_values Map of parameters and values
	 * @param actorGroups ActorGroups object
	 * @param a Actors that you want to run the action on/with
	 */	
	@SuppressWarnings("rawtypes")
	public abstract void run(Parameters parameters, State state, Actor actor);
}
