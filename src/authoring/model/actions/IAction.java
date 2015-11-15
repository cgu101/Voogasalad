package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.bundles.Identifiable;

/**
 * @author Inan
 *
 */
public interface IAction extends Identifiable {
	public void run(ActorGroups actorGroup, Actor... a);
}