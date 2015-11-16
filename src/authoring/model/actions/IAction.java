package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;

/**
 * @author Inan
 *
 */
public interface IAction {
	public void run(ActorGroups actorGroup, Actor... a);
}
