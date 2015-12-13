package network.core.connections;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public interface ICloseable {
	
	public abstract void close() throws Exception;
	public Boolean isClosed();
}
