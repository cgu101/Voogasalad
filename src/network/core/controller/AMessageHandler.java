// This entire file is part of my masterpiece.
// Christopher Streiffer
package network.core.controller;

import java.util.HashMap;
import java.util.Map;

import network.core.messages.format.Request;

public abstract class AMessageHandler<T> {
	
	private Map<Request, T> executers;
	
	public AMessageHandler() {
		executers = new HashMap<Request, T>();
		init(executers);
	}
	
	public T getHandler(Request type) {
		return executers.get(type);
	}
	
	protected abstract void init(Map<Request, T> executers);
	
}
