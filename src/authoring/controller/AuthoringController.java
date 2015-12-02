package authoring.controller;

import java.util.List;

import authoring.controller.constructor.AuthoringActorConstructor;
import authoring.controller.constructor.ConstructorFactory;
import authoring.controller.constructor.LevelConstructor;
import authoring.model.game.Game;
import data.XMLManager;
import exceptions.data.GameFileException;

public class AuthoringController {
	
	private LevelConstructor levelConstructor;
	private AuthoringActorConstructor authActorConstructor;
	
	/**
	 * Constructor for AuthroingController. Creates new Level and AuthoringActor Constructor instances. 
	 */
	public AuthoringController() {
		levelConstructor = ConstructorFactory.getLevelConstructor();
		authActorConstructor = ConstructorFactory.getAuthoringActorConstructor();
	}
	
	/**
	 * This method returns a new instance of a LevelConstructor. This method should be called 
	 * when the user wants to build a new level.
	 * 
	 * @return LevelConstructor 
	 */
	public LevelConstructor getLevelConstructor() {
		return levelConstructor;
	}

	/**
	 * This method returns a new instance of a AuthoringActorConstructor. This method should be called 
	 * when the user wants to build a new level.
	 * 
	 * @return AuthoringActorConstructor 
	 */
	public AuthoringActorConstructor getAuthoringActorConstructor() {
		return authActorConstructor;
	}
	
	/**
	 * This method takes in a game object and filename, and writes this game to an xml file.
	 * 
	 * @param game
	 * @param fileLocation
	 * @throws GameFileException
	 */
	public void saveGame (Game game, String fileLocation) throws GameFileException {
		XMLManager.saveGame(game, fileLocation);
	}

	/**
	 * This method will generate a Game object from the given list of LevelConstructor
	 * instances. 
	 * 
	 * @param levelBuilderList
	 * @return
	 */
	public static Game getGameWithLevels (List<LevelConstructor> levelBuilderList) {
		Game game = new Game();
		for (int i = 0 ; i < levelBuilderList.size(); i++) {
			game.addLevel(levelBuilderList.get(i).buildLevel(Integer.toString(i)));
		}
		return game;
		
	}
}
