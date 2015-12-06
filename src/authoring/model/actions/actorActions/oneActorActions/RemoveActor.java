package authoring.model.actions.actorActions.oneActorActions;

import java.util.Map;

import authoring.model.actions.actorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;

/**
 * @author Inan
 *
 */
public class RemoveActor<V> extends AOneActorAction<V>{
	@Override
	public void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor a) {
		// TODO Auto-generated method stub
		actorGroups.killActor(a);
		System.out.println("REMOVING ACTOR");	
	}
}