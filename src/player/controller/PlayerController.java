package player.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.properties.Property;
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
import player.IPlayer;
import player.SpriteManager;

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
			mySpriteManager.updateSprites(getActorList(), this.myScene);
			
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public ArrayList<Actor> getActorList(){
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for(Bundle<Actor> b : myEngine.getActors().values()){
			actors.addAll(b.getComponents().values());
		}
		return actors;
	}
	
	public ArrayList<Property<?>> getProperties(Actor a){
		ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
		Bundle<Property<?>> propBundle = a.getProperties();
		for(Property<?> b : propBundle){
			properties.add((Property<?>) b.getValue());
		}
		return properties;
	}
	
	public Map<String,Bundle<Actor>> getActorMap(){
		return myEngine.getActors();
	}
	
	public Map<String, String> getPropertyStringMap(Actor a){
		Map<String, String> propertyMap = new HashMap<String, String>();
		Bundle<Property<?>> b = a.getProperties(); 
		for(Property<?> prop : b){
			String identifier = prop.getUniqueID(); //health or whatever
			String value = String.valueOf(prop.getValue());
			propertyMap.put(identifier, value);
		}
		return propertyMap;
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