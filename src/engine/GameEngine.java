package engine;

import java.util.HashMap;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.level.Level;
import exceptions.EngineException;
import player.InputManager;

public class GameEngine implements IEngine {
	private static final String DEFAULT_INPUTS_FILENAME = "resources/gameplayer/Inputs";

	private Game game;
	private InteractionExecutor levelExecutor;
	
	public GameEngine () {
		this(null);
	}
	
	public GameEngine (Game game) {
		this.game = game;
		init(game);
	}

	@Override
	public void init(Game gameData) {
		this.game = gameData;
		init(gameData.getLevel("0"));
	}
	
	// TODO:
	public void init(Level level) {
		levelExecutor = new InteractionExecutor(level, new InputManager(DEFAULT_INPUTS_FILENAME));
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
//	public State ejectState () {
//	Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>();
//	propertyBundle.add(new Property<String>(CURRENT_LEVEL, currentLevel.getUniqueID()));
//	return new State(propertyBundle, executor.getActors());
//}
//public void injectState (State state) {
//	Level level = levelBundle.getProperty((String) state.getProperty(CURRENT_LEVEL).getValue());
//	init(level);
//	executor.setActors(state.getActorMap());
//}
}