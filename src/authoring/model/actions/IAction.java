package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;

/**
 * @author Inan
 *
 */
public interface IAction {
	/**
	 * @param actorGroup ActorGroups object
	 * @param a Actor that you want to run the action on/with
	 */
	public void run(ActorGroups actorGroup, Actor... a);
}
