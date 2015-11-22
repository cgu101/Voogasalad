package authoring.controller.constructor;

import authoring.model.level.Level;

public class LevelConstructor {
	
	private ActorGroupsConstructor actorConstructor;
	private TreeConstructor treeConstructor;
	
	LevelConstructor() {
		actorConstructor = new ActorGroupsConstructor();
		treeConstructor = new TreeConstructor();
	}
	
	/**
	 * Returns an ActorGroupContructor. 
	 * 
	 * @return ActorGroupsConstructor
	 */
	public ActorGroupsConstructor getActorGroupsConstructor() {
		return actorConstructor;
	}
	
	/**
	 * Returns a TreeConstructor.
	 * 
	 * @return TreeConstructor
	 */
	public TreeConstructor getTreeConstructor() {
		return treeConstructor;
	}
	
	/**
	 * This method will return the Level that has been built by the user through the API calls
	 * to TreeConstructor and ActorGroupsContructor. 
	 * 
	 * @param level
	 * @return Level
	 */
	public Level buildLevel(String level) {
		Level ret = new Level(level);
		ret.setActorGroupsValues(actorConstructor);
		ret.setTreeConstructorValues(treeConstructor);
		return ret;
	}

}
