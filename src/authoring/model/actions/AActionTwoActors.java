package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 *
 */
public abstract class AActionTwoActors implements IAction {

	@Override
	public void run(Parameters parameters, State state, Actor... a) {
		run(parameters, state, a[0], a[1]);
		if(a.length>2){
			System.out.println(this.getClass().getName()+": More than 2 actors as arguments!!");
		}
	}

	public abstract void run (Parameters parameters, State state, Actor a, Actor b);
}
