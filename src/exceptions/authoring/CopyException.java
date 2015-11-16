package exceptions.authoring;

import exceptions.AuthoringException;

public class CopyException extends AuthoringException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CopyException (String err, String argument) {
		super(String.format(err, argument));
	}
}
