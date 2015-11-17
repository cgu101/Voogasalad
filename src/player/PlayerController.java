package player;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import controller.AController;
import data.IFileManager;
import data.XMLManager;
import engine.GameEngine;
import engine.IEngine;
import exceptions.EngineException;
import exceptions.data.GameFileException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayerController extends AController {

	Stage myStage;
	IEngine myEngine;
	IFileManager myXMLManager;
	Timeline myGameLoop;
	SpriteManager mySpriteManager;
	int fps = 10;
	

	public PlayerController() {
		myEngine = new GameEngine();
		myXMLManager = new XMLManager();
		mySpriteManager = new SpriteManager();
	}
	
	// should be called by front end
	public void loadGame(String fileName) throws GameFileException {
		try {
			Game game = myXMLManager.loadGame(fileName);
			myEngine = new GameEngine();
			myEngine.init(game);
			start();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadGame (File file) {
		try {
			myXMLManager.testLoadGame(file);
		} catch (GameFileException e) {
			System.out.println("Test has failed");
		}
	}
	
	/*
	private void initializeGame(Game game) throws EngineException {
		myEngine.init(game);
	}*/

	public Stage getStage () {
		return myStage;
	}
	
	public void start() {
		KeyFrame frame = new KeyFrame(new Duration(10000/this.fps), e -> this.run());
		Timeline myGameLoop = new Timeline();
		myGameLoop.setCycleCount(Timeline.INDEFINITE);
		myGameLoop.getKeyFrames().add(frame);
		myGameLoop.play();
	}

	public void pause() {		
		myGameLoop.pause();
	}
	
	public void resume() {
		myGameLoop.play();
	}
	
	public void save() {
		// serialize and save Engine or InteractionExectutor?
	}

	public void run(){
		try {
			myEngine.play();
			this.render(myEngine.getActors());
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void render(Map<String, Bundle<Actor>> actorMap){
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for(Bundle<Actor> b : actorMap.values()){
			for(Actor a : b){
				actors.add(a);
			}
		}
		mySpriteManager.updateSprites(actors);
	}

}
