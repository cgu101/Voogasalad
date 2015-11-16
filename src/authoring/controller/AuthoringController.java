package authoring.controller;

import authoring.controller.constructor.ConstructorFactory;
import authoring.controller.constructor.LevelConstructor;
import controller.AController;
import javafx.stage.Stage;
import view.screen.AbstractScreen;
import view.screen.CreatorScreen;

public class AuthoringController extends AController {

	public AuthoringController (Stage stage) {
		this(stage, new CreatorScreen());
	}
	
	public AuthoringController(Stage stage, AbstractScreen screen) {
		super(stage, screen);
	}
	
	public LevelConstructor getLevelConstructor() {
		return ConstructorFactory.getLevelConstructor();
	}
	
	public AuthoringConfigManager getAuthoringConfigManager() {
		return AuthoringConfigManager.getInstance();
	}
	
	@Override
	public void run() throws Exception {
		currentScreen.run();
	}

}
