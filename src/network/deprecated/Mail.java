package network.deprecated;

import java.io.Serializable;
import java.util.Deque;

/**
 * @author Austin Liu (abl17) and Chris Streiffer (cds33)
 *
 */

public interface Mail extends Serializable {
	public RequestType getRequest();
	
	public Serializable getData ();
	
	public Deque<String> getPath ();
}
