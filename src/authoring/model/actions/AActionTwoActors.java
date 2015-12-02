package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
<<<<<<< HEAD
=======
import authoring.model.tree.Parameters;
>>>>>>> 27802045563a11f9e6637745de2cbdb3e2121331

/**
 * @author Inan
 *
 */
public abstract class AActionTwoActors implements IAction {

	@Override
	public void run(Parameters parameters, ActorGroups actorGroup, Actor... a) {
		run(parameters, actorGroup, a[0], a[1]);
		if(a.length>2){
			System.out.println(this.getClass().getName()+": More than 2 actors as arguments!!");
		}
	}

	public abstract void run (Parameters parameters, ActorGroups actorGroup, Actor a, Actor b);
}
