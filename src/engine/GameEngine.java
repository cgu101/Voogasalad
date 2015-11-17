package engine;

import java.util.HashMap;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.level.Level;
import engine.runnable.RunnableGame;
import exceptions.EngineException;
import player.InputManager;

public class GameEngine implements IEngine {

	public RunnableGame runnableGame;
	public Game game;
	
	public GameEngine () {
		this(null);
	}
	
	public GameEngine (Game game) {
		init(game);
	}

	@Override
	public void init(Game gameData) {
		runnableGame = new RunnableGame(gameData);
	}
	
	// TODO:
	public void init(Level level) {
		InteractionExecutor executor = new InteractionExecutor(level, new InputManager("resources/gameplayer/Inputs"));
	}
	
	@Override
	public void reset() {
		runnableGame.reset();
	}

	@Override
	public void load(Game game) {
		 runnableGame.load(game);
	}

	@Override
	public void play () throws EngineException {
		runnableGame.run();
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