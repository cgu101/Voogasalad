package network.test;

import java.io.Serializable;

public class TestMessage implements Serializable {
	private static final String DEFAULT_MESSAGE = "Testing";
	public String message; //Temp Testing
	
	public TestMessage () {
		this.message = DEFAULT_MESSAGE;
	}
}
