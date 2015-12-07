package authoring.model.actions;

import java.io.Serializable;
import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;

/**
 * @author Inan
 * @param <V>
 *
 */
public interface IAction extends Serializable {
	/**
	 * @param parameters_values Map of actor Parameters and the Values that you want to use in the action
	 * @param state State object
	 * @param a Actors that you want to run the action on/with
	 */
	@SuppressWarnings("rawtypes")
	public void run(Parameters parameters, State state, Actor... actors);
}
