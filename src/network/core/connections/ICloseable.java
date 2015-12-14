// This entire file is part of my masterpiece.
// Christopher Streiffer
package network.core.connections;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 * 
 * Describes an object that displays closing behaviors and a recognition of state.
 */

public interface ICloseable {
	
	/**
	 * Changes the closed state of the interface to 'open' or
	 * Changes the open state of the interface to 'closed'
	 * @throws Exception
	 */
	public abstract void close() throws Exception;
	
	/**
	 * Describes the state that the interface is in:
	 * True - Closed State
	 * False - Open State
	 * @return Closed State
	 */
	public Boolean isClosed();
}
