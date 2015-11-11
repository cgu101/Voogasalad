package authoring;

import data.UpdateObject;

public interface ILevel {
	
	public UpdateObject run();
	public boolean initGraph();
	public boolean addActor(IActor actor);
	public boolean linkActorsWithTriggers(ITriggerEvent myEvent, IActor... actors);

}
