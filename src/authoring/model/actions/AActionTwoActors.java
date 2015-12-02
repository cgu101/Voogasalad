package authoring.model.actions;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorGroups;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;

/**
 * @author Inan
 *
 */
public abstract class AActionTwoActors implements IAction {

	@Override
	public void run(Parameters parameters, ActorGroups actorGroup, Actor... a) {
		run(parameters, actorGroup, a[0], a[1]);
		if(a.length>2){
			System.out.println(this.getClass().getName()+": More than 2 actors as arguments!!");
		}
	}

	public abstract void run (Parameters parameters, ActorGroups actorGroup, Actor a, Actor b);
	
	@SuppressWarnings("unchecked")
	public Double distance(Actor a, Actor b) {
		Double xA = ((Property<Double>) a.getProperties().getComponents().get("xLocation")).getValue();
		Double yA = ((Property<Double>) a.getProperties().getComponents().get("yLocation")).getValue();
		Double xB = ((Property<Double>) b.getProperties().getComponents().get("xLocation")).getValue();
		Double yB = ((Property<Double>) b.getProperties().getComponents().get("yLocation")).getValue();
		return Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));
	}
}
