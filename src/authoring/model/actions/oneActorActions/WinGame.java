package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;

public class WinGame extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = 6822754399215993372L;
	private String levelKey = PropertyKeyResource.getKey(PropertyKey.LEVEL_ID_KEY);
	private String nextLevelKey = PropertyKeyResource.getKey(PropertyKey.NEXT_LEVEL_KEY);

	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor a) {
		//TODO
	}
}
