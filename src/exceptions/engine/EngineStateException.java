package exceptions.engine;

import exceptions.EngineException;

public class EngineStateException extends EngineException {

	/**
	 * EngineStateException Serial ID
	 */
	private static final long serialVersionUID = 1L;

	public EngineStateException(String err, String argument) {
		super(err, argument);
	}

}
