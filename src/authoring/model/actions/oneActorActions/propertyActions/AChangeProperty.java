// This entire file is part of my masterpiece.
// Inan Tainwala

package authoring.model.actions.oneActorActions.propertyActions;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import authoring.model.actions.oneActorActions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;
import player.InputManager;

public abstract class AChangeProperty<V> extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -3550470371445425654L;

	public abstract void run(Property<?> property, V value, Actor a);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public final void run(InputManager inputManager, Parameters parameters, State state, Actor a){
		Set<Entry<String, V>> entrySet = parameters.getParameterAndValues();
		for(Map.Entry<String, V> entry : entrySet){
			Property<?> prop = a.getProperty(entry.getKey());
			run(prop, entry.getValue(), a);
		}

	}
}
