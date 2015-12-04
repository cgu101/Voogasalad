package authoring.model.actions;

import java.util.Map;

import authoring.model.actors.Actor;
import engine.State;

/**
 * @author Inan
 * @param <V>
 *
 */
public interface IAction<V> {
	
	/**
	 * @param parameters_values Map of actor Parameters and the Values that you want to use in the action
	 * @param state State object
	 * @param a Actors that you want to run the action on/with
	 */
	public void run(Map<String, V> parameters_values, State state, Actor... a);
}
