package player.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.properties.Property;
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
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import player.IPlayer;
import player.InputManager;
import player.SpriteManager;
import view.element.ActorMonitor;

public class PlayerController implements IPlayer {

	private static final String DEFAULT_INPUTS_FILENAME = "resources/gameplayer/Inputs";

	private Scene myScene;
	private IEngine myEngine;
	private Timeline myGameLoop;
	private SpriteManager mySpriteManager;
	private InputManager myInputManager;
	private ActorMonitor actorMonitor;
	private static int fps = 10;
	private int refreshComponentRate;
	
	// TODO: TEMP
	private State currentState;

	/**
	 * Player Controller Constructor
	 * This method creates a new instance of a PlayerController.
	 *
	 * @param  screen The Screen used to determine dimensions of the component
	 */
	public PlayerController(Scene s) {
		mySpriteManager = new SpriteManager(s);
		myScene = s;
		myInputManager = new InputManager();
		myEngine = new GameEngine(myInputManager);
		refreshComponentRate = 0;
		attachInputs(s);
	}

	private void attachInputs(Scene s) {
		s.addEventFilter(KeyEvent.KEY_PRESSED, e -> myInputManager.keyPressed(e));
		s.addEventFilter(KeyEvent.KEY_RELEASED, e -> myInputManager.keyReleased(e));
	}

	// should be called by front end
	public void loadGame(String fileName) throws GameFileException, EngineException {
		System.out.println("PlayController.loadGame(" + fileName + ")");
		Game game = XMLManager.loadGame(fileName);
		myEngine.init(game);
		start();
	}

	/**
	 * This method grabs the PlayerController's scene.
	 */
	public Scene getScene() {
		return myScene;
	}

	/**
	 * This method starts a GameLoop.
	 */
	public void start() {
		KeyFrame frame = new KeyFrame(new Duration(1000 / this.fps), e -> this.run());
		myGameLoop = new Timeline();
		myGameLoop.setCycleCount(Timeline.INDEFINITE);
		myGameLoop.getKeyFrames().add(frame);
		myGameLoop.play();
		System.out.println("Game started...");
	}

	/**
	 * This method pauses the GameLoop.
	 */
	public void pause() throws GameFileException {
		try {
			myGameLoop.pause();
			mySpriteManager.pause();
		} catch (NullPointerException e) {
			throw new GameFileException();
		}
	}

	/**
	 * This method resumes the GameLoop.
	 */
	public void resume() throws GameFileException {
		try {
			myGameLoop.play();
			mySpriteManager.resume();
		} catch (NullPointerException e) {
			throw new GameFileException();
		}
	}

	private void run() {
		try {
			// TODO: myEngine.play() returns a State
			currentState = myEngine.play();
			mySpriteManager.updateSprites(getIndividualActorsList(), this.myScene);
			refreshPlayerComponents();
			actorMonitor.refresh();	
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * This method grabs the actors from the state returned by the engine.
	 *
	 * @return the list of Actors.
	 */
	public ArrayList<Actor> getIndividualActorsList() {
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for (Bundle<Actor> b : currentState.getActorMap().getMap().values()) {
			actors.addAll(b.getComponents().values());
		}
		return actors;
	}
	
	/**
	 * This method grabs the the Game Properties from the Game Engine.
	 * The keys are the property type, and the values are the property values.
	 * 
	 * @return The map of Properties.
	 */
	public Map<String, String> getGameProperties(){
		Map<String, String> properties = new HashMap<String, String>();
		Bundle<Property<?>> propBundle = myEngine.getProperties();
		for(Property<?> b : propBundle){
			properties.put(b.getUniqueID(), b.getValue().toString());
		}
		return properties;
	}

	/**
	 * This method grabs a list of properties of the specified Actor 'a'.
	 *
	 * @param a The Actor you would like to retrieve the properties for.
	 * @return The list of Properties.
	 */
	public ArrayList<Property<?>> getProperties(Actor a){
		ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
		Bundle<Property<?>> propBundle = a.getProperties();
		for(Property<?> b : propBundle){
			properties.add((Property<?>) b.getValue());
		}
		return properties;
	}

	/**
	 * This method creates a Map the actor's Property Identifier to it's value
	 * The map is in <String, String> format to allow for easy GUI display.
	 *
	 * @param a The Actor you would like to retrieve the properties for.
	 * @return The map of properties identifier -> value casted to Strings.
	 */
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
	
	/**
	 * This method creates a Map the actor's Property Identifier to it's value
	 * The map is in <String, String> format to allow for easy GUI display.
	 *
	 * @param a The Actor you would like to retrieve the properties for.
	 * @param group The group classification of the Actor you're looking for.
	 * 
	 * @return The map of properties identifier -> value casted to Strings.
	 */
	public Map<String, String> getPropertyStringMapFromActorString(String a){
		Actor match = null;
		for(Actor actor : getIndividualActorsList()){
			if(actor.getUniqueID().equals(a)){
				match = actor;	
				break;
			}
		}
		if(match != null){
			return getPropertyStringMap(match);
		}
		return null;
	}
	
	public Actor getActorFromString(String a){
		ArrayList<Actor> actorList = getIndividualActorsList();
		for(Actor curr : actorList){
			if(curr.getUniqueID().equals(a)){
				return curr;
			}
		}
		return null;
	}
	
	/**
	 * This method creates an XML of the Game's saved state.
	 * 
	 * @param fileName Name that the file should be stored as.
	 */
	public void saveState (String fileName) throws GameFileException {
		pause();
		State saveState;
		try {
			saveState = myEngine.saveState();
			XMLManager.saveState(saveState, fileName);
		} catch (EngineStateException e) {
			throw new GameFileException(e.getMessage());
		}
		resume();
	}

	/**
	 * This method loads an XML of a Game's saved state.
	 * 
	 * @param fileName The XML file's name that should be loaded.
	 */
	public void loadState(String fileName) throws GameFileException {
		pause();
		State saveState = XMLManager.loadState(fileName);
		try {
			myEngine.loadState(saveState);
		} catch (EngineException e) {
			throw new GameFileException(e.getMessage());
		}
		resume();
	}

	public void addMonitor(ActorMonitor monitor) {
		this.actorMonitor = monitor;
		
	}

	private void refreshPlayerComponents() {
		refreshComponentRate++;
		if(refreshComponentRate > 20){
			actorMonitor.refresh();
			refreshComponentRate = 0;
		}
	}
}
