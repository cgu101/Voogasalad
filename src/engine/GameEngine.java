package engine;

import java.util.HashMap;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.level.Level;
import authoring.model.properties.Property;
import exceptions.EngineException;
import player.InputManager;

public class GameEngine implements IEngine {
	private static final String DEFAULT_INPUTS_FILENAME = "resources/gameplayer/Inputs";
	private static final String LEVEL_ID_KEY = "level";

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
	public void load(Game game) {
		//TODO:
		// what is this supposed to do?
	}

	@Override
	public void play () throws EngineException {
		EngineHeartbeat heartbeat = levelExecutor.run();
		// do something with the heartbeat
	}

	@Override
	public Map<String, Bundle<Actor>> getActors() {
		//TODO
		return new HashMap<String, Bundle<Actor>>();
		
	}
	@Override
	public State ejectState () {
		Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>();
		propertyBundle.add(new Property<String>(LEVEL_ID_KEY, levelExecutor.getLevelID()));
		return new State(propertyBundle, levelExecutor.getActors());
	}
	@Override
	public void injectState (State state) {
		Level level = game.getLevel((String) state.getProperty(LEVEL_ID_KEY).getValue());
		init(level);
		levelExecutor.setActors(state.getActorMap());
	}
}