package player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
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

public class PlayerController {

	IEngine myEngine;
	IFileManager myXMLManager;
	Timeline myGameLoop;
	int fps = 10;
	

	public PlayerController() {
		myEngine = new GameEngine();
		myXMLManager = new XMLManager();
	}
	
	// should be called by front end
	private void loadGame(String fileName) throws GameFileException {
		try {
			Game game = myXMLManager.loadGame(fileName);
			myEngine.init(game);
			start();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	private void initializeGame(Game game) throws EngineException {
		myEngine.init(game);
	}*/

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
		
		
	}

}
