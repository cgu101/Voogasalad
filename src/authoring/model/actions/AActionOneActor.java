package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Group;
import authoring.model.bundles.Identifiable;

/**
 * @author Inan
 *
 */
public abstract class AActionOneActor implements IAction {

	public abstract void run(ActorGroups actorMap, Actor a);

	@Override
	public void run(ActorGroups actorMap, Actor... a){
		for(Actor actor: a){
			run (actorMap, actor);
		}
		if(a.length>1){
			System.out.println(this.getClass().getName()+": More than 1 actor as argument!!. Running Action on all Actors");
		}
	}
	
	
}
