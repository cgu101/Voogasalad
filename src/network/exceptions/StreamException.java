package network.exceptions;

import network.core.connections.threads.ThreadType;

public class StreamException extends NetworkException {
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 3392196465172433472L;
	private static final String ERROR_MESSAGE = "Error while trying to close or open stream in ";

	public StreamException (ThreadType t) {
		super(ERROR_MESSAGE + t.toString());
	}
}
