package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.bundles.Group;

public abstract class AActionTwoActors implements IAction {

	@Override
	public void run(Group actorGroup, Actor... a) {
		run(actorGroup, a[0], a[1]);
	}

	public abstract void run (Group actorGroup, Actor a, Actor b);
}
