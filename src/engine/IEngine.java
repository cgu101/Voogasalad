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

	public void init(Game gameData) throws EngineException;
	
	public void reset ();
	public EngineHeartbeat play() throws EngineException;
	public Map<String, Bundle<Actor>> getActors();
	public State ejectState() throws EngineStateException;
	public void injectState(State state) throws EngineException;
}
