package player;

import java.util.List;

import authoring.ALevel;
import authoring.ILevel;
import controller.AController;
import data.IFileManager;
import data.XMLManager;
import data.model.GameData;
import engine.GameEngine;
import engine.IEngine;
import exceptions.EngineException;
import exceptions.data.GameFileException;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.screen.PlayerScreen;

public class PlayerController extends AController implements IPlayer {
	
	// TODO: contains gui
	IEngine myEngine;
	IFileManager myXMLManager;
	Timeline myGameLoop;

	public PlayerController (Stage stage) {
		super(stage, new PlayerScreen());
		myEngine = new GameEngine();
		myXMLManager = new XMLManager();
	}


	private GameData selectGame(String fileName) throws GameFileException {
		return myXMLManager.loadGame(fileName);
	}
	
	private void initializeGame (GameData game) throws EngineException {
		myEngine.init(game);
	}



	@Override
	public void play() {
		myGameLoop.play();
	}

	@Override
	public void pause() {
		
		// should not pause the loop, changes engine to do nothing
		
	}

	@Override
	public void renderGui(Scene s) {
		// TODO Auto-generated method stub
		
		// TODO player GUI classes
	}


	@Override
	public void run() throws EngineException {
		// TODO Auto-generated method stub
		myEngine.play();
		currentScreen.run();
	}

}
