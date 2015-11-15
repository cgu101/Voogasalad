package authoring.model.actions;

import authoring.model.actors.Actor;

public abstract class AActionOneActor implements IAction{

	public abstract void run(ActorGroups actorMap, Actor a);

	@Override
	public void run(ActorGroups actorMap, Actor... a){
		for(Actor actor: a){
			run (actorMap, actor);
		}
	}
}
