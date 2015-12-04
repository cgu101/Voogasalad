package authoring.model.actions.oneActorActions;

import authoring.model.actions.AActionOneActor;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 *
 */
public class RemoveActor extends AActionOneActor{
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		ActorGroups actorGroup = state.getActorMap();
		actorGroup.removeActor(actor);
		System.out.println("REMOVING ACTOR");
	}
}