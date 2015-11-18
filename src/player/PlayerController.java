package player;

import java.util.ArrayList;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import data.IFileManager;
import data.XMLManager;
import engine.GameEngine;
import engine.IEngine;
import engine.State;
import exceptions.EngineException;
import exceptions.data.GameFileException;
import exceptions.engine.EngineStateException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

public class PlayerController implements IPlayer {

	Scene myScene;
	IEngine myEngine;
	IFileManager myXMLManager;
	Timeline myGameLoop;
	SpriteManager mySpriteManager;
	int fps = 10;
	

	public PlayerController(Scene s) {
		myEngine = new GameEngine();
		myXMLManager = new XMLManager();
		mySpriteManager = new SpriteManager(s);
		myScene = s;
	}
	
	// should be called by front end
	public void loadGame(String fileName) throws GameFileException {
		System.out.println("PlayController.loadGame(" + fileName + ")");
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
	
	/*public void loadGame (File file) {
		this.loadGame(file.getName());
		try {
			myXMLManager.testLoadGame(file);
		} catch (GameFileException e) {
			System.out.println("Test has failed");
		}
	}*/
	
	/*
	private void initializeGame(Game game) throws EngineException {
		myEngine.init(game);
	}*/

	public Scene getScene () {
		return myScene;
	}
	
	public void start() {
		KeyFrame frame = new KeyFrame(new Duration(1000/this.fps), e -> this.run());
		myGameLoop = new Timeline();
		myGameLoop.setCycleCount(Timeline.INDEFINITE);
		myGameLoop.getKeyFrames().add(frame);
		myGameLoop.play();
		System.out.println("Game started...");
	}

	public void pause() throws GameFileException {	
		try {			
			myGameLoop.pause();
			mySpriteManager.pause();
		} catch (NullPointerException e){
			throw new GameFileException();
		}
	}
	
	public void resume() throws GameFileException {
		try {
			myGameLoop.play();
			mySpriteManager.resume();
		} catch (NullPointerException e){
			throw new GameFileException();
		}
	}

	private void run(){
		try {
			myEngine.play().call(this);
			this.render(myEngine.getActors());
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void render(Map<String, Bundle<Actor>> actorMap){
		//System.out.println(actorMap);
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for(Bundle<Actor> b : actorMap.values()){
			actors.addAll(b.getComponents().values());
		}
		mySpriteManager.updateSprites(actors, this.myScene);
	}

	public void saveState (String fileName) throws GameFileException {
		pause();
		State saveState;
		try {
			saveState = myEngine.ejectState();
			myXMLManager.saveState(saveState, fileName);
		} catch (EngineStateException e) {
			throw new GameFileException(e.getMessage());
		}
		resume();
	}
	public void loadState (String fileName) throws GameFileException {
		pause();
		State saveState = myXMLManager.loadState(fileName);
		try {
			myEngine.injectState(saveState);
		} catch (EngineException e) {
			throw new GameFileException(e.getMessage());
		}
		resume();
	}
}
