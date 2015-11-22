package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;

/**
 * @author Inan
 *
 */
public abstract class AActionOneActor implements IAction {

	/**
	 * @param actorGroups ActorGroups object
	 * @param a Actor that you want to run the action on/with
	 */
	public abstract void run(ActorGroups actorGroups, Actor a);

	@Override
	public void run(ActorGroups actorGroups, Actor... a){
		for(Actor actor: a){
			run (actorGroups, actor);
		}
		if(a.length>1){
			System.out.println(this.getClass().getName()+": More than 1 actor as argument!!. Running Action on all Actors");
		}
	}
}
