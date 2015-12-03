package engine;

import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.level.Level;
import authoring.model.properties.Property;
import exceptions.EngineException;
import exceptions.engine.EngineStateException;
import player.InputManager;

/**
 * The game engine, which handles loading single levels and level transitions.
 */

public class GameEngine implements IEngine {
	private static final String LEVEL_ID_KEY = "level";
	private static final String GAME_ID_KEY = "name";
	private static final String INITIAL_LEVEL = "0";
	private static final String INITIAL_LEVEL_KEY = "Initial Level";

	private Game game;
	private InteractionExecutor levelExecutor;
	private InputManager inputManager;
	
	public GameEngine (InputManager inputManager) {
		this.inputManager = inputManager;
		this.game = null;
		this.levelExecutor = null;
	}
	
	/**
	 * Loads a game into the engine and loads the first level (level with ID "0").
	 */
	@Override
	public void init(Game game) {
		this.game = game;
		String levelID = getLevelID(game);
		Level initialLevel = makeLevel(levelID);

		Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>();
		propertyBundle.add(new Property<String>(LEVEL_ID_KEY, levelID));
		propertyBundle.add(new Property<String>(GAME_ID_KEY, (String) game.getProperty(GAME_ID_KEY).getValue()));
		levelExecutor = new InteractionExecutor(initialLevel, inputManager, new State(propertyBundle, null));

	}
	private String getLevelID (Game g) {
		Object levelProperty = g.getProperty(INITIAL_LEVEL_KEY);
		String levelID = INITIAL_LEVEL;
		if (levelProperty != null) {
			levelID = levelProperty.toString();
		}
		return levelID;
	}
	private Level makeLevel(String levelID) {
		return game.getLevel(levelID);
	}

	/**
	 * Loads the first level of the game (level with ID "0")
	 */
	@Override
	public void reset() throws EngineException {
		init(game);
	}
	
	/**
	 * Calls run method of the current {@link InteractionExecutor}.
	 * @return A {@link EngineHeartbeat} for the player controller to call.
	 */
	@Override
	public EngineHeartbeat play () throws EngineException {
		return levelExecutor.run();
	}
	
	/**
	 * @return The current actor map of the level executor.
	 */
	@Override
	public Map<String, Bundle<Actor>> getActors() {
		return levelExecutor.getActors().getMap();
	}
	
	// TODO: rewrite save/load state
	/**
	 * @return A {@link State} to be saved.
	 */
	@Override
	public State saveState () throws EngineStateException {
		Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>();
		propertyBundle.add(new Property<String>(LEVEL_ID_KEY, levelExecutor.getLevelID()));
		propertyBundle.add(new Property<String>(GAME_ID_KEY, (String) game.getProperty(GAME_ID_KEY).getValue()));
		return new State(propertyBundle, levelExecutor.getActors());
	}
	/**
	 * Loads a save state into the engine. If the state does not match the current game, a {@link EngineStateException} is thrown.
	 */
	@Override
	public void loadState (State state) throws EngineException {
		if (state.getProperty(GAME_ID_KEY).getValue().equals(game.getProperty(GAME_ID_KEY).getValue())) {
			Level level = game.getLevel((String) state.getProperty(LEVEL_ID_KEY).getValue());
			levelExecutor = new InteractionExecutor(level, inputManager, state);
			levelExecutor.setActors(state.getActorMap());
		} else {
			throw new EngineStateException("Wrong game", null);
		}
	}

	@Override
	public void nextLevel() throws EngineException {
		// TODO: get the id of the next level
		Level iLevel = makeLevel(INITIAL_LEVEL);
		// TODO STATE into InteractionExecutor
		levelExecutor = new InteractionExecutor(iLevel, inputManager, null);
		
	}

	@Override
	public void replayLevel() throws EngineException {
		// TODO Auto-generated method stub
		
	}
}