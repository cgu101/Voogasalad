package engine;

import java.util.HashMap;
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
	private static final String DEFAULT_INPUTS_FILENAME = "resources/gameplayer/Inputs";
	private static final String LEVEL_ID_KEY = "level";
	private static final String GAME_ID_KEY = "name";

	private Game game;
	private InteractionExecutor levelExecutor;
	
	public GameEngine () {
		this(new Game());
	}
	
	public GameEngine (Game game) {
		init(game);
	}

	@Override
	public void init(Game gameData) {
		this.game = gameData;
		init(gameData.getLevel("0"));
	}
	
	// TODO:
	public void init(Level level) {
		if (level == null) {
			level = new Level("testLevel");
			levelExecutor = new InteractionExecutor();
		} else {
			levelExecutor = new InteractionExecutor(level, new InputManager(DEFAULT_INPUTS_FILENAME));
		}
	}
	
	@Override
	public void reset() {
		init(game.getLevel("0"));
	}

	@Override
	public EngineHeartbeat play () throws EngineException {
		return levelExecutor.run();
	}

	@Override
	public Map<String, Bundle<Actor>> getActors() {
		return levelExecutor.getActors().getMap();
	}
	@Override
	public State ejectState () throws EngineStateException {
		Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>();
		propertyBundle.add(new Property<String>(LEVEL_ID_KEY, levelExecutor.getLevelID()));
		propertyBundle.add(new Property<String>(GAME_ID_KEY, (String) game.getProperty(GAME_ID_KEY).getValue()));
		return new State(propertyBundle, levelExecutor.getActors());
	}
	@Override
	public void injectState (State state) throws EngineException {
		if (state.getProperty(GAME_ID_KEY).getValue().equals(game.getProperty(GAME_ID_KEY).getValue())) {
			Level level = game.getLevel((String) state.getProperty(LEVEL_ID_KEY).getValue());
			init(level);
			levelExecutor.setActors(state.getActorMap());
		} else {
			throw new EngineStateException("Wrong game", null);
		}
	}
}