package network.framework.format;

public interface Proxy {
	public void send (Object o);
	
	public boolean isConnected ();
}
