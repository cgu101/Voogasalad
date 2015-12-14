package authoring.model.triggers.noactorconditions;

import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import player.InputManager;

/**
 * Checks if the key code or mouse button specified is currently being pressed.
 * 
 * @author Tyler
 */
public class KeyOrMousePress extends AInputTrigger {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3282663990769521946L;

	/**
	 * Checks if the key code or mouse button specified in the Parameters object
	 * is currently being pressed
	 * 
	 * @param parameters
	 *            contains a KeyCode or MouseButton as its first and only
	 *            parameter value
	 * @param inputManager
	 *            the class that manages all key and mouse inputs
	 * @return true if being pressed, false if not
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <K extends Enum<K>> boolean evaluate(Parameters parameters, InputManager inputManager) {
		K buttonName = (K) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0");
		return inputManager.getValue(buttonName);
	}

	/**
	 * @return true if key or mouse input condition is met, false if not
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager) {
		return evaluate(parameters, inputManager);
	}
}
