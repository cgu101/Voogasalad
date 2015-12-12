package network.core.connections;

public interface ICloseable {
	
	public abstract void close() throws Exception;
	public Boolean isClosed();
}
