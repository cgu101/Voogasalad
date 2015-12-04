package engine;

import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.properties.Property;
import exceptions.EngineException;
import exceptions.engine.EngineStateException;

/**
 * Basic implementation of the engine is as follows:
 * 
 * Capability to init a game based on a list of levels or a GameData class
 * Capability to run a game --> 
 * 
 * @author Austin
 *
 */

public interface IEngine {

	/**
	 * Initializes a game based on a Game class
	 * @param gameData
	 * @return 
	 * @throws EngineException
	 */
	public void init(Game gameData);
	
	/**
	 * Resets the entire game to its initial load state
	 */
	public void reset () throws EngineException;
	
	/**
	 * Plays the game and returns an State object representing functionality for
	 * a single iteration
	 * 
	 * @return State
	 * @throws EngineException
	 */
	public State play() throws EngineException;
	
	/**
	 * Switches the Level
	 * 
	 * @throws EngineException
	 */
	public void nextLevel() throws EngineException;
	
	/**
	 * Replays the Level
	 * 
	 * @throws EngineException
	 */
	public void replayLevel() throws EngineException;
	
	/**
	 * Ejects a state
	 * 
	 * @return State
	 * @throws EngineStateException
	 */
	public State saveState() throws EngineStateException;
	
	/**
	 * Injects a state
	 * 
	 * @param state
	 * @throws EngineException
	 */
	public void loadState (State state) throws EngineException;
	
	/**
	 * Returns the properties corresponding to a game
	 * @return Map <String, Bundle<Property>>
	 */
	public Bundle<Property<?>> getProperties();
}
