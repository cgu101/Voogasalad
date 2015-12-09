package authoring.controller.constructor.levelwriter;

import java.util.HashMap;
import java.util.Map;

import authoring.controller.constructor.configreader.ResourceType;
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
	public Level buildLevel(Level ret) {
//		ret.setActorGroupsValues(actorConstructor);
//		ret.setTreeConstructorValues(treeConstructor);
		ret.setMapConstructorValues(mapConstructor);
		return ret;
	}

	public void buildConstructor (Level level) {
		actorConstructor.setActorGroups(level.getActorGroups());
		((TreeConstructor) treeConstructor).setRootTree(level.getRootTree());
		Map<ResourceType, Map> myMap = new HashMap<ResourceType, Map>();
//		myMap.put(ResourceType.TRIGGERS, level.getTriggerMap());
//		myMap.put(ResourceType.ACTIONS, level.getActionMap());
//		
//		mapConstructor.setItemsMap(myMap);
	}
}
