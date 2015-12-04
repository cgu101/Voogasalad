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
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;

/**
 * The game engine, which handles loading single levels and level transitions.
 */

public class GameEngine implements IEngine {
	private static final String INITIAL_LEVEL = "0";

	private String gameKey = PropertyKeyResource.getKey(PropertyKey.GAME_ID_KEY);
	private String levelKey = PropertyKeyResource.getKey(PropertyKey.LEVEL_ID_KEY);
	private String initialLevelKey = PropertyKeyResource.getKey(PropertyKey.INITIAL_LEVEL_KEY);
	private String levelCountKey = PropertyKeyResource.getKey(PropertyKey.GAME_LEVEL_COUNT_KEY);
	
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
		String levelID = getFirstLevelName(game);
		Level initialLevel = makeLevel(game, levelID);

		Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>();
		propertyBundle.add(new Property<String>(levelKey, levelID));
		// TODO: force game to have a name
		propertyBundle.add(new Property<String>(gameKey, (String) game.getProperty(gameKey).getValue()));
		setExecutor(initialLevel, new State(propertyBundle,null));

	}
	private String getFirstLevelName (Game g) {
		Object levelProperty = g.getProperty(initialLevelKey);
		String levelID = INITIAL_LEVEL;
		if (levelProperty != null) {
			levelID = levelProperty.toString();
		}
		return levelID;
	}
	private Level makeLevel(Game g, String levelID) {
		return g.getLevel(levelID);
	}
	private Level makeLevel(String levelID) {
		if (Integer.parseInt(levelID) < 0) {
			return makeDefaultNextLevel(levelID);
		}
		return game.getLevel(levelID);
	}
	private Level makeDefaultNextLevel (String currentLevelID) {
		int nextLevelID = Integer.parseInt(currentLevelID) + 1;
		if (nextLevelID >= Integer.parseInt((String)game.getProperty(levelCountKey).getValue())) {
			return null;
		}
		String nextLevelName = Integer.toString(nextLevelID);
		return game.getLevel(nextLevelName);
	}
	private void setExecutor(Level level, State state) {
		levelExecutor = new InteractionExecutor(level, inputManager, state);
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
		EngineHeartbeat heartbeat = levelExecutor.run();
		processState(heartbeat.getState());
		return heartbeat;
	}
	
	private void processState(State state) {
		String levelID = (String) state.getProperty(levelKey).getValue();
		if (!levelID.equals(levelExecutor.getLevelID())) {
			// SWITCH LEVEL
			setExecutor(makeLevel(levelID), state);
		}
		
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
		propertyBundle.add(new Property<String>(levelKey, levelExecutor.getLevelID()));
		propertyBundle.add(new Property<String>(gameKey, (String) game.getProperty(gameKey).getValue()));
		return new State(propertyBundle, levelExecutor.getActors());
	}
	/**
	 * Loads a save state into the engine. If the state does not match the current game, a {@link EngineStateException} is thrown.
	 */
	@Override
	public void loadState (State state) throws EngineException {
		if (state.getProperty(gameKey).getValue().equals(game.getProperty(gameKey).getValue())) {
			Level level = game.getLevel((String) state.getProperty(levelKey).getValue());
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

	/**
	 * @return The property map of the current game.
	 */
	@Override
	public Bundle<Property<?>> getProperties() {
		return game.getProperties();
	}
	
//	ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
//	Bundle<Property<?>> propBundle = game.getProperties();
//	for(Property<?> b : propBundle){
//		properties.add((Property<?>) b.getValue());
//	}
//	return properties;
}