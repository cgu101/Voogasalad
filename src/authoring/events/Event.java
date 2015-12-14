package authoring.events;

import java.util.List;

import authoring.events.interfaces.IAction;

public class Event {
	List<String> actorIDs;
	IAction action;
	List<String> arguments;
	
	public Event () {
		
	}
}
