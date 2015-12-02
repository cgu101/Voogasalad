package exceptions.engine;

import exceptions.EngineException;

public class InteractionTreeException extends EngineException {

	/**
	 * EngineStateException Serial ID
	 */
	private static final long serialVersionUID = 1L;

	public InteractionTreeException(String err, String argument) {
		super(err, argument);
	}

}
