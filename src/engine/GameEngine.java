package engine;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorType;
import authoring.model.bundles.Bundle;
import authoring.model.game.ActorDependencyInjector;
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
	private String nextLevelKey = PropertyKeyResource.getKey(PropertyKey.NEXT_LEVEL_KEY);
	
	private Game game;
	private InteractionExecutor levelExecutor;
	private InputManager inputManager;
	private ActorDependencyInjector depInjector;
	
	public GameEngine (InputManager inputManager) {
		this.inputManager = inputManager;
		this.game = null;
		this.levelExecutor = null;
		this.depInjector = new ActorDependencyInjector(this);
	}
	
	/**
	 * Loads a game into the engine and loads the first level (level with ID "0").
	 */
	@Override
	public void init(Game game) {
		this.game = game;
		String levelID = getFirstLevelName(game);
		Level initialLevel = makeLevel(game, levelID);

		Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>(game.getProperties());
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
		if (levelID.equals(nextLevelKey)) {
			return makeDefaultNextLevel(levelExecutor.getLevelID());
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
		levelExecutor = new InteractionExecutor(level, inputManager, state, depInjector);
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
	public State play () throws EngineException {
		State heartbeat = levelExecutor.run();
		processState(heartbeat);
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
	 * Return the current State of the game (Actor and Properties)
	 */
	public State getState(){
		return levelExecutor.getCurrentState();
	}

	/**
	 * @return The current actor map of the level executor.
	 */
	
	// TODO: rewrite save/load state
	/**
	 * @return A {@link State} to be saved.
	 */
	@Override
	public State saveState () throws EngineStateException {
		return new State(levelExecutor.getCurrentState());
	}
	/**
	 * Loads a save state into the engine. If the state does not match the current game, a {@link EngineStateException} is thrown.
	 */
	@Override
	public void loadState (State state) throws EngineException {
		if (state.getProperty(gameKey).getValue().equals(game.getProperty(gameKey).getValue())) {
			Level level = game.getLevel((String) state.getProperty(levelKey).getValue());
			levelExecutor = new InteractionExecutor(level, inputManager, state, depInjector);
		} else {
			throw new EngineStateException("Wrong game", null);
		}
	}

	// This method shouldn't really be used
	@Override 
	public void nextLevel() throws EngineException {
		changeDependencies();
		setExecutor(makeDefaultNextLevel(levelExecutor.getLevelID()),levelExecutor.getCurrentState());
	}
	
	private void changeDependencies () {
		this.depInjector = new ActorDependencyInjector(this);
	}

	@Override
	public void replayLevel() throws EngineException {
		// TODO: ACTUALLY MAKE IT REPLAY
		System.out.println("Not implemented!!");
	}
	
//	ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
//	Bundle<Property<?>> propBundle = game.getProperties();
//	for(Property<?> b : propBundle){
//		properties.add((Property<?>) b.getValue());
//	}
//	return properties;
	
	public void displayError (String errorMessage) {
		//TODO
		System.err.println(errorMessage);
	}
}