package authoring.model.actions.parameterActions;

import authoring.model.actions.AParameterAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import engine.State;
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;

public class LevelTransition<V> extends AParameterAction<V>  {
	private String levelKey = PropertyKeyResource.getKey(PropertyKey.LEVEL_ID_KEY);
	@Override
	public void run(Property<?> property, V value, State state, Actor a) {
		String nextLevelID = (String) value;
		state.getProperty(levelKey).setValue(nextLevelID);
		property.setValue(nextLevelID);
	}

}

