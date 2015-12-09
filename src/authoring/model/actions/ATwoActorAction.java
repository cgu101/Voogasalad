package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

/**
 * @author Inan
 *
 */
public abstract class ATwoActorAction implements IAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -7737671247397850357L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManager, Parameters parameters, State state, Actor... actors) {
		run(inputManager, parameters, state, actors[0], actors[1]);
		state.getActorMap().addActor(actors[0], actors[1]);
	}
	
	/**
	 * @param parameters_values Map of parameters and values
	 * @param actorGroups ActorGroups object
	 * @param a Actors that you want to run the action on/with
	 */	
	@SuppressWarnings("rawtypes")
	public abstract void run(InputManager inputManger, Parameters parameters, State state, Actor a, Actor b);
}
