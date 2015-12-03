package network.framework;

import java.io.Serializable;

public class SGameState implements Serializable {
	private String myName;
	
	public SGameState (String name) {
		myName = name;
	}
	
	public String getName () {
		return myName;
	}
}
