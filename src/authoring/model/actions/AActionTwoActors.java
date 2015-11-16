package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.bundles.Group;

/**
 * @author Inan
 *
 */
public abstract class AActionTwoActors implements IAction {

	@Override
	public void run(ActorGroups actorGroup, Actor... a) {
		run(actorGroup, a[0], a[1]);
		if(a.length>2){
			System.out.println(this.getClass().getName()+": More than 2 actors as arguments!!");
		}
	}

	public abstract void run (ActorGroups actorGroup, Actor a, Actor b);
}
