package engine.test.level;

import authoring.tests.interfaces.IAction;
import authoring.tests.interfaces.IWorker;

public class Level implements IWorker {

	/**
	 * List of Actors
	 * List of Objectives
	 * List of Actors --> (Next Actors)
	 * Map<String, IAction> actionMap
	 */
	
	@Override
	public IAction getAction(String actionName) {
		return null;
	}
	
	/**
	 * Update () {
	 * 		ObjectivesList.forEach(goal -> goal.update());
	 * 		ActorList.forEach(acotr -> actor.update()); //Update animates
	 * 		
	 * }
	 */

}
