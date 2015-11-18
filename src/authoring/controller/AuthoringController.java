package authoring.controller;

import java.util.List;

import authoring.controller.constructor.AuthoringActorConstructor;
import authoring.controller.constructor.ConstructorFactory;
import authoring.controller.constructor.LevelConstructor;
import authoring.model.game.Game;
import controller.AController;
import data.IFileManager;
import data.XMLManager;
import exceptions.data.GameFileException;
import javafx.stage.Stage;
import view.screen.AbstractScreen;
import view.screen.CreatorScreen;

public class AuthoringController extends AController {

	IFileManager myXMLManager;
	
	public AuthoringController() {
		myXMLManager = new XMLManager();
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
	
	@Override
	public void run() throws Exception {
		currentScreen.run();
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
