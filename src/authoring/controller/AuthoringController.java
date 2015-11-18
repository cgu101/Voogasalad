package authoring.controller;

import java.util.List;

import authoring.controller.constructor.AuthoringActorConstructor;
import authoring.controller.constructor.ConstructorFactory;
import authoring.controller.constructor.LevelConstructor;
import authoring.model.game.Game;
import data.IFileManager;
import data.XMLManager;
import exceptions.data.GameFileException;

public class AuthoringController {

	IFileManager myXMLManager;
	
	public AuthoringController() {
		myXMLManager = new XMLManager();
	}
	
	public LevelConstructor getLevelConstructor() {
		return ConstructorFactory.getLevelConstructor();
	}
	
	public AuthoringActorConstructor getAuthoringActorConstructor() {
		return ConstructorFactory.getAuthoringActorConstructor();
	}
	
	public void saveGame (Game game, String fileLocation) throws GameFileException {
		myXMLManager.saveGame(game, fileLocation);
	}

	public Game getGameWithLevels (List<LevelConstructor> levelBuilderList) {
		Game game = new Game();
		for (int i = 0 ; i < levelBuilderList.size(); i++) {
			game.addLevel(levelBuilderList.get(i).buildLevel(Integer.toString(i)));
		}
		return game;
		
	}
}
