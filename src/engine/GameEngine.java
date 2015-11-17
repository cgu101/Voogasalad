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
}