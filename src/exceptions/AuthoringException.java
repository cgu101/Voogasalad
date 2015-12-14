package exceptions;

/**
 * @author Austin
 */
public abstract class AuthoringException extends Exception {

	public AuthoringException(String format) {
		super(format);
	}

	/**
	 * Creates a dummy serial ID
	 */
	private static final long serialVersionUID = 1L;
	
}
