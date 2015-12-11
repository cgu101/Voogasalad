package network.framework.format;

import java.io.Serializable;
import java.util.Deque;

import network.deprecated.RequestType;

public interface Mail extends Serializable {
	public RequestType getRequest();
	
	public Serializable getData ();
	
	public Deque<String> getPath ();
}
