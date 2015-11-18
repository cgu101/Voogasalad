package authoring.controller;

import java.util.List;

import authoring.controller.constructor.AuthoringActorConstructor;
import authoring.controller.constructor.ConstructorFactory;
import authoring.controller.constructor.LevelConstructor;
import authoring.model.game.Game;

public class AuthoringController {

	public AuthoringController() {
	}
	
	public LevelConstructor getLevelConstructor() {
		return ConstructorFactory.getLevelConstructor();
	}
	
	public AuthoringActorConstructor getAuthoringActorConstructor() {
		return ConstructorFactory.getAuthoringActorConstructor();
	}

	public Game getGameWithLevels (List<LevelConstructor> levelBuilderList) {
		Game game = new Game();
		for (int i = 0 ; i < levelBuilderList.size(); i++) {
			game.addLevel(levelBuilderList.get(i).buildLevel(Integer.toString(i)));
		}
		return game;
		
	}
}
