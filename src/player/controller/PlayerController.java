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
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import player.IPlayer;
import player.InputManager;
import player.SpriteManager;
import view.element.ActorMonitor;

public class PlayerController implements IPlayer {

	private Scene myScene;
	private IEngine myEngine;
	private Timeline myGameLoop;
	private SpriteManager mySpriteManager;
	private InputManager myInputManager;
	private ActorMonitor actorMonitor;
	private static int fps = 10;
	private int refreshComponentRate;
	

	/**
	 * 	 * Player Controller Constructor
	 * This method creates a new instance of a PlayerController.
	 *
	 * @param s the Scene used to determine size
	 * @param pane the pane to make the map
	 */
	public PlayerController(Scene s, GridPane pane) {
		mySpriteManager = new SpriteManager(s, pane);
		myScene = s;
		myInputManager = new InputManager();
		myEngine = new GameEngine(myInputManager);
		refreshComponentRate = 0;
		attachInputs(s);
	}

	private void attachInputs(Scene s) {
		s.addEventFilter(KeyEvent.KEY_PRESSED, e -> myInputManager.keyPressed(e));
		s.addEventFilter(KeyEvent.KEY_RELEASED, e -> myInputManager.keyReleased(e));
		
		s.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> myInputManager.mousePressed(e));
		s.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> myInputManager.mouseReleased(e));
	}

	// should be called by front end
	public void loadGame(String fileName) throws GameFileException, EngineException {
		boolean first = myGameLoop != null;
		if (first) {
			pause();
		}
		Game game = XMLManager.loadGame(fileName);
		myEngine.init(game);
		if (first) actorMonitor.resetData();
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
		KeyFrame frame = new KeyFrame(new Duration(1000 / fps), e -> this.run());
		myGameLoop = new Timeline();
		myGameLoop.setCycleCount(Timeline.INDEFINITE);
		myGameLoop.getKeyFrames().add(frame);
		myGameLoop.play();
		mySpriteManager.resume();
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
			consumeInstruction(myEngine.play());
			mySpriteManager.updateSprites(getIndividualActorsList(), this.myScene);
			myEngine.getState().areThereNewOrDeadActors();
			refreshPlayerComponents();
			actorMonitor.refresh();	
		} catch (EngineException e) {
			e.printStackTrace();
		}
	}


	private void consumeInstruction(State state) {
		if (state.getInstruction() != null) {
			state.getInstruction().apply(this);
			state.setInstruction(null);
		}
	}

	/**
	 * This method grabs the actors from the state returned by the engine.
	 *
	 * @return the list of Actors.
	 */
	public ArrayList<Actor> getIndividualActorsList() {
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for (Bundle<Actor> b : myEngine.getState().getActorMap().getMap().values()) {
			actors.addAll(b.getComponents().values());
		}
		return actors;
	}
	
	/**
	 * This method grabs the list of Actor Groups from the state returned by the engine.
	 *
	 * @return the list of Actor Groups.
	 */
	public ArrayList<String> getActorGroups() {
		ArrayList<String> actorGroups = new ArrayList<String>();
		for(String s :myEngine.getState().getActorMap().getMap().keySet()){
			actorGroups.add(s);
		}
		return actorGroups;
	}
	
	/**
	 * This method grabs the the Game Properties from the Game Engine.
	 * The keys are the property type, and the values are the property values.
	 * 
	 * @return The map of Properties.
	 */
	public Map<String, String> getGameProperties(){
		Map<String, String> properties = new HashMap<String, String>();
		Bundle<Property<?>> propBundle = myEngine.getState().getPropertyBundle();
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
		
		return getPropertyStringMap(match);
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
			actorMonitor.resetData();
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
	
	public State getState(){
		return myEngine.getState();
	}
	
	public void resetGame() throws GameFileException{
		try {
			myEngine.reset();
			actorMonitor.resetData();
		} catch (EngineException e) {
			throw new GameFileException(e.getMessage());
		}
	}
	
	public void replayLevel() throws GameFileException{
		pause();
		try {
			myEngine.replayLevel();
			actorMonitor.resetData();
		} catch (EngineException e) {
			throw new GameFileException(e.getMessage());
		}
		resume();
	}
	
	public view.map.Map getMap(){
		return mySpriteManager.getMap();
	}

	public void endGame() {
		try {
			pause();
		} catch (GameFileException e) {
			// TODO 
			e.printStackTrace();
			((GameEngine) myEngine).displayError(e.getMessage());
		}
		
	}
	public void updateBackground(String filename) {
		mySpriteManager.getMap().updateBackground(new Image(filename));
	}

}
