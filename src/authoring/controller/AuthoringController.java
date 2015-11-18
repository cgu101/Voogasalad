package authoring.controller;

import java.util.List;

import authoring.controller.constructor.AuthoringActorConstructor;
import authoring.controller.constructor.ConstructorFactory;
import authoring.controller.constructor.LevelConstructor;
import authoring.model.game.Game;

public class AuthoringController {
	
	private LevelConstructor levelConstructor;
	private AuthoringActorConstructor authActorConstructor;

	public AuthoringController() {
		levelConstructor = ConstructorFactory.getLevelConstructor();
		authActorConstructor = ConstructorFactory.getAuthoringActorConstructor();
	}
	
	public LevelConstructor getLevelConstructor() {
		return levelConstructor;
	}
	
	public AuthoringActorConstructor getAuthoringActorConstructor() {
		return authActorConstructor;
	}

	public Game getGameWithLevels (List<LevelConstructor> levelBuilderList) {
		Game game = new Game();
		for (int i = 0 ; i < levelBuilderList.size(); i++) {
			game.addLevel(levelBuilderList.get(i).buildLevel(Integer.toString(i)));
		}
		return game;
		
	}
}
