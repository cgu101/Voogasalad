package exceptions;

public abstract class EngineException extends Exception {

	/**
	 * Exceptions related to the Engine
	 * Filler Serial Version ID
	 * TODO: Interpret this, or change this
	 *
	 */
	
	private static final long serialVersionUID = 3L;

	public EngineException (String err, String argument) {
		super(String.format(err, argument));
	}
}
