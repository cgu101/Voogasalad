package authoring.model.actions.parameterActions;

import authoring.model.actions.AParameterAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import engine.State;
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;

public class LevelTransitionToNext<V>  extends AParameterAction<V>   {
	private String levelKey = PropertyKeyResource.getKey(PropertyKey.LEVEL_ID_KEY);
	@Override
	public void run(Property<?> property, V value, State state, Actor a) {
		int nextLevelID = Integer.parseInt((String)state.getProperty(levelKey).getValue()) + 1;
		state.getProperty(levelKey).setValue(Integer.toString(nextLevelID));
		property.setValue(Integer.toString(nextLevelID));
	}
}
