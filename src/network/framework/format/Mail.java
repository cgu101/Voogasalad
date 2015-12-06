package network.framework.format;

import java.io.Serializable;
import java.util.Deque;

public interface Mail extends Serializable {
	public Request getRequest();
	
	public Serializable getData ();
	
	public Deque<String> getPath ();
}
