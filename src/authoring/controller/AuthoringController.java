package authoring.controller;

import java.util.List;

import authoring.controller.constructor.AuthoringActorConstructor;
import authoring.controller.constructor.ConstructorFactory;
import authoring.controller.constructor.LevelConstructor;
import authoring.model.game.Game;
import controller.AController;
import javafx.stage.Stage;
import view.screen.AbstractScreen;
import view.screen.CreatorScreen;

public class AuthoringController extends AController {

	public AuthoringController() {
	}
	
	public AuthoringController (Stage stage) {
		this(stage, new CreatorScreen());
	}
	
	public AuthoringController(Stage stage, AbstractScreen screen) {
		super(stage, screen);
	}
	
	public LevelConstructor getLevelConstructor() {
		return ConstructorFactory.getLevelConstructor();
	}
	
	public AuthoringActorConstructor getAuthoringActorConstructor() {
		return ConstructorFactory.getAuthoringActorConstructor();
	}
	
	public AuthoringConfigManager getAuthoringConfigManager() {
		return AuthoringConfigManager.getInstance();
	}
	
	@Override
	public void run() throws Exception {
		currentScreen.run();
	}

	public Game getGameWithLevels (List<LevelConstructor> levelBuilderList) {
		Game game = new Game();
		for (int i = 0 ; i < levelBuilderList.size(); i++) {
			game.addLevel(levelBuilderList.get(i).buildLevel(Integer.toString(i)));
		}
		return game;
		
	}
}
