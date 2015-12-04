package authoring.model.actions.actorActions;

import java.util.Map;

import authoring.model.actions.AActorActions;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;

/**
 * @author Inan
 * @param <V>
 *
 */
public abstract class AOneActorAction<V> extends AActorActions<V> {
	@Override
	public final void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor... a){
		if(a.length>1){
			System.out.println(this.getClass().getName()+": More than 1 actor as argument!!. Running Action on all Actors");
		}
		run(parameters_values, actorGroups, a[0]);
	}

	public abstract void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor a);
}
