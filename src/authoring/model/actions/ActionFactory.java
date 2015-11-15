package authoring.model.actions;

import java.util.HashMap;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.properties.Property;

public class ActionFactory {

	private Map<String, IAction> actions;

	public ActionFactory() {
		actions = new HashMap<String, IAction>();
		initMap();
	}

	private void initMap(){
		actions.put("move", new IAction() {
			@Override
			public void run(Actor... actors){
				Actor actor = actors[0];
				Property<Integer> health = (Property<Integer>) actor.getProperties().getComponents().get("health");
				Integer h = health.getValue();
				health.setValue(++h);
				System.out.println("Health: "+health.getValue());
			}
		});
	}

}
