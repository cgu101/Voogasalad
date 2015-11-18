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

public class GameEngine implements IEngine {
	private static final String LEVEL_ID_KEY = "level";
	private static final String GAME_ID_KEY = "name";

	private Game game;
	private InteractionExecutor levelExecutor;
	private InputManager inputManager;
	
	public GameEngine (InputManager inputManager) {
		this.inputManager = inputManager;
	}
	/**
	 * Loads a game into the engine and loads the first level (level with ID "0").
	 */
	@Override
	public void init(Game game) {
		this.game = game;
		init(game.getLevel("0"), inputManager);
	}
	
	private void init(Level level, InputManager inputManager) {
		this.inputManager = inputManager;
		// TODO
		if (level == null) {
			level = new Level("testLevel");
			levelExecutor = new InteractionExecutor();
		} else {
			levelExecutor = new InteractionExecutor(level, inputManager);
		}
	}
	/**
	 * Loads the first level of the game (level with ID "0")
	 */
	@Override
	public void reset() {
		init(game.getLevel("0"), inputManager);
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
	/**
	 * @return A {@link State} to be saved.
	 */
	@Override
	public State ejectState () throws EngineStateException {
		Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>();
		propertyBundle.add(new Property<String>(LEVEL_ID_KEY, levelExecutor.getLevelID()));
		propertyBundle.add(new Property<String>(GAME_ID_KEY, (String) game.getProperty(GAME_ID_KEY).getValue()));
		return new State(propertyBundle, levelExecutor.getActors());
	}
	/**
	 * Loads a save state into the engine. If the state does not match the current game, a {@link EngineStateException} is thrown.
	 */
	@Override
	public void injectState (State state) throws EngineException {
		if (state.getProperty(GAME_ID_KEY).getValue().equals(game.getProperty(GAME_ID_KEY).getValue())) {
			Level level = game.getLevel((String) state.getProperty(LEVEL_ID_KEY).getValue());
			init(level, inputManager);
			levelExecutor.setActors(state.getActorMap());
		} else {
			throw new EngineStateException("Wrong game", null);
		}
	}
}