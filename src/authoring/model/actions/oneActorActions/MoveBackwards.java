package authoring.model.actions.oneActorActions;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import engine.State;

public class MoveBackwards extends AOneActorAction {
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -920846466857319661L;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(Parameters parameters, State state, Actor actor) {
		Double angle = ((Property<Double>) actor.getProperty("angle")).getValue();
		Double speed = ((Property<Double>) actor.getProperty("speed")).getValue();
		Double x = ((Property<Double>) actor.getProperty("xLocation")).getValue();
		Double y = ((Property<Double>) actor.getProperty("yLocation")).getValue();
		
		x = x - Math.cos(Math.toRadians(angle)) * speed; 
		y = y - Math.sin(Math.toRadians(angle)) * speed;

		((Property<Double>)actor.getProperty("xLocation")).setValue(x);
		((Property<Double>)actor.getProperty("yLocation")).setValue(y);
	}
}
