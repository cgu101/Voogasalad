package data;

import java.util.List;

import authoring.model.actions.IAction;
import authoring.model.actors.IActor;
import authoring.model.triggers.ITriggerEvent;

public interface TemplateGraph {
	
	/**
	 * Obtains the events relevant to a link of actors (a-b)
	 * 
	 * @param a
	 * @param b
	 * @return List<ITriggerEvent>
	 */
	public List<ITriggerEvent> getEvents(IActor a, IActor b);
	
	/**
	 * Lists the actions associated with an individual actor (a)
	 * 
	 * @param a
	 * @return List<IAction>
	 */
	public List<IAction> getActions(IActor a);

}
