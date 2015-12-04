package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 *
 */
public interface IAction {
	/**
	 * @param actorGroup ActorGroups object
	 * @param a Actor that you want to run the action on/with
	 */
	public void run(Parameters parameters, State state, Actor... a);
}
