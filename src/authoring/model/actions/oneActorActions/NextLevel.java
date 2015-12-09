package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;

public class NextLevel extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 4542051448186712167L;

	private String levelKey = PropertyKeyResource.getKey(PropertyKey.LEVEL_ID_KEY);
	private String nextLevelKey = PropertyKeyResource.getKey(PropertyKey.NEXT_LEVEL_KEY);

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		state.getPropertyBundle().add(new Property<String>(levelKey, nextLevelKey));
	}
}
