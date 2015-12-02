package engine;

import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
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
	 * @throws EngineException
	 */
	public void init(Game gameData);
	
	/**
	 * Resets the entire game to its initial load state
	 */
	public void reset () throws EngineException;
	
	/**
	 * Plays the game and returns an EngineHeartbeat object representing functionality for
	 * a single iteration
	 * 
	 * @return EngineHeartbeat
	 * @throws EngineException
	 */
	public EngineHeartbeat play() throws EngineException;
	
	/**
	 * Returns the actors corresponding to a game
	 * @return Map <String, Bundle<Actor>>
	 */
	public Map<String, Bundle<Actor>> getActors();
	
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
}
