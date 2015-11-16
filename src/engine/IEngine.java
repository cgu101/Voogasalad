package engine;

import authoring.model.game.Game;
import exceptions.EngineException;

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
	public void load(Game game);
	public void play() throws EngineException;
}
