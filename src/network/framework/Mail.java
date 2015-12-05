package network.framework;

import java.io.Serializable;

public interface Mail {
	public Request getRequest();
	
	public Serializable getData ();
}
