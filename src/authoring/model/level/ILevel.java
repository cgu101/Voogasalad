package authoring.model.level;

import authoring.model.actors.IActor;
import authoring.model.triggers.ITriggerEvent;

public interface ILevel {
	
	public boolean initGraph();
	public boolean addActor(IActor actor);
	public boolean linkActorsWithTriggers(ITriggerEvent myEvent, IActor... actors);

}
