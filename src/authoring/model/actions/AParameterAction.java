package authoring.model.actions;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import engine.State;

public abstract class AParameterAction<V> implements IAction<V> {
	
	@Override
	public final void run(Map<String, V> parameters_values, State state, Actor... a){
		if(a.length>1){
			System.out.println("More than one actor. Applying action to each parameter of each actor!!");
		}
		for(Actor actor: a){
			Set<Entry<String, V>> entrySet = parameters_values.entrySet();
			for(Map.Entry<String, V> entry : entrySet){
				Property<?> prop = actor.getProperty(entry.getKey());
				run(prop, entry.getValue(), state, actor);
			}
		}
		ActorGroups actorGroups = state.getActorMap();
		actorGroups.addActor(a);
	}

	public abstract void run(Property<?> property, V value, State state, Actor a);
}
