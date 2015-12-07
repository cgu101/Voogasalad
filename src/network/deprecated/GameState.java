package network.deprecated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.tree.InteractionTreeNode;
import view.level.LevelMap;

public class GameState implements Serializable {

	private ArrayList<LevelState> levels;
	private LevelState currentLevel;

	public GameState(List<LevelMap> inputLevels, LevelMap currentLevel) {
		for (LevelMap level : inputLevels) {
			LevelState levelState = configure(level);
			levels.add(levelState);
		}
		this.currentLevel = configure(currentLevel);
	}

	private LevelState configure(LevelMap level) {
		Map<String, Bundle<Actor>> actorMap = level.getController().getLevelConstructor().getActorGroupsConstructor()
				.getActorGroups().getMap();
		InteractionTreeNode rootTree = level.getController().getLevelConstructor().getTreeConstructor().getRootTree();

		return new LevelState(level.getTab().getId(), actorMap, rootTree);
	}

	/**
	 * This method will retrieve the complete list of levels.
	 */
	public ArrayList<LevelState> getLevels() {
		return levels;
	}

	/**
	 * This method will retrieve the current level.
	 */
	public LevelState getCurrentLevel() {
		return currentLevel;
	}

	// *** -> Indicates what we've determined is relevant to send for now
	// Each tab indicates a get function can be used to grab

	// Current Level (id/title/background image)
	// ***Title (int id)
	// ***BackgroundImage (Image)
	// AuthoringController
	// levelConstructor
	// ActorGroupsConstructor
	// ActorGroups
	// ***actorMap
	// TreeConstructor
	// ***rootTree
	// MapConstructor
	// authoringActorConstructor
	// ActorGroupsConstructor
	// getActorGroups
	// getMap (ActorMap)

}
