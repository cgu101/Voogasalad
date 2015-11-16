package player;

import java.util.List;

import authoring.model.game.Game;
import authoring.model.level.ALevel;
import authoring.model.level.ILevel;
import controller.AController;
import data.IFileManager;
import data.XMLManager;
import engine.GameEngine;
import engine.IEngine;
import exceptions.EngineException;
import exceptions.data.GameFileException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.screen.PlayerScreen;

public class PlayerController extends AController implements IPlayer {

	IEngine myEngine;
	IFileManager myXMLManager;
	Timeline myGameLoop;
	int fps = 10;

	public PlayerController(Stage stage) {
		super(stage, new PlayerScreen());
		myEngine = new GameEngine();
		myXMLManager = new XMLManager();
	}

	private void loadGame(String fileName) throws GameFileException {
		try {
			myEngine.init(myXMLManager.loadGame(fileName));
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	private void initializeGame(Game game) throws EngineException {
		myEngine.init(game);
	}*/

	@Override
	public void play() {
		KeyFrame frame = new KeyFrame(new Duration(10000/this.fps), e -> this.run());
		Timeline myGameLoop = new Timeline();
		myGameLoop.setCycleCount(Timeline.INDEFINITE);
		myGameLoop.getKeyFrames().add(frame);
		myGameLoop.play();
	}

	@Override
	public void pause() {		
		myGameLoop.pause();
	}
	
	public void save() {
		// serialize and save Engine or InteractionExectutor?
	}

	@Override
	public void run(){
		try {
			myEngine.play();
			myEngine.getActors();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
