package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.bundles.Group;

public abstract class AActionOneActor implements IAction{

	public abstract void run(Group actorMap, Actor a);

	@Override
	public void run(Group actorMap, Actor... a){
		for(Actor actor: a){
			run (actorMap, actor);
		}
	}
}
