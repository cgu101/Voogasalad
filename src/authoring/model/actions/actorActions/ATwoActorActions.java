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
public abstract class ATwoActorActions<V> extends AActorActions<V> {

	
	@Override
	public final void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor... a) {
		run(parameters_values, actorGroups, a[0], a[1]);
		if(a.length>2){
			System.out.println(this.getClass().getName()+": More than 2 actors as arguments!!");
		}
	}

	/**
	 * @param parameters_values Map of parameters and values
	 * @param actorGroups ActorGroups object
	 * @param a Actor 1 that you want to run the action on/with
	 * @param a Actor 2 that you want to run the action on/with
	 */
	public abstract void run(Map<String, V> parameters_values, ActorGroups actorGroups, Actor a, Actor b);
	
}
