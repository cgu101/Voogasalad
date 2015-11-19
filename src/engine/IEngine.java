package engine;

import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import exceptions.EngineException;
import exceptions.engine.EngineStateException;
import player.InputManager;

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
	public void init(Game gameData) throws EngineException;
	
	/**
	 * Resets the entire game to its initial load state
	 */
	public void reset ();
	
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
	public State ejectState() throws EngineStateException;
	
	/**
	 * Injects a state
	 * 
	 * @param state
	 * @throws EngineException
	 */
	public void injectState(State state) throws EngineException;
}
