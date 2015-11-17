package authoring.controller.constructor;

import authoring.model.level.Level;

public class LevelConstructor {
	
	private ActorGroupsConstructor actorConstructor;
	private TreeConstructor treeConstructor;
	
	LevelConstructor() {
		actorConstructor = new ActorGroupsConstructor();
		treeConstructor = new TreeConstructor();
	}
	
	public ActorGroupsConstructor getActorGroupsConstructor() {
		return actorConstructor;
	}
	
	public TreeConstructor getTreeConstructor() {
		return treeConstructor;
	}
	
	public Level buildLevel(String level) {
		Level ret = new Level(level);
		ret.setActorGroupsValues(actorConstructor);
		ret.setTreeConstructorValues(treeConstructor);
		return ret;
	}

}
