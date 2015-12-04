package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 *
 */
public abstract class AActionTwoActors implements IAction {
	public abstract void run (Parameters parameters, State state, Actor a, Actor b);

	@Override
	public void run(Parameters parameters, State state, Actor... a) {
		run(parameters, state, a[0], a[1]);
	}
}
