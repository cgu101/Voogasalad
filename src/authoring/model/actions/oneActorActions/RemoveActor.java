package authoring.model.actions.oneActorActions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

public class RemoveActor extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -5082699694858815653L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		state.getActorMap().killActor(actor);
	}
}