package authoring.model.actions;

import authoring.model.actors.Actor;

public abstract class AActionTwoActors implements IAction {

	@Override
	public void run(ActorGroups actorGroup, Actor... a) {
		run(actorGroup, a[0], a[1]);
	}

	public abstract void run (ActorGroups actorGroup, Actor a, Actor b);
}
