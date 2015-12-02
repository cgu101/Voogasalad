package authoring.controller.constructor.levelwriter;

import authoring.controller.constructor.levelwriter.interfaces.ITreeConstructor;
import authoring.model.level.Level;

public class LevelConstructor {
	
	private ActorGroupsConstructor actorConstructor;
	private ITreeConstructor treeConstructor;
	private MapConstructor mapConstructor;
	
	public LevelConstructor() {
		actorConstructor = new ActorGroupsConstructor();
		treeConstructor = new TreeConstructor();
		mapConstructor = new MapConstructor();
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
	public ITreeConstructor getTreeConstructor() {
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
		ret.setMapConstructorValues(mapConstructor);
		return ret;
	}

}
