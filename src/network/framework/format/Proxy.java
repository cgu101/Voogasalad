package network.framework.format;

/**
 * @author Austin Liu (abl17) and Chris Streiffer (cds33)
 *
 */
public interface Proxy {
	public void send (Object o);
	
	public boolean isConnected ();
}
