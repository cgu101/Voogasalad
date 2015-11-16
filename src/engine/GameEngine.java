package engine;

import authoring.model.level.Level;
import data.model.GameData;
import engine.runnable.RunnableGame;
import exceptions.EngineException;
import player.InputManager;

public class GameEngine implements IEngine {

	public RunnableGame runnableGame;
	
	public GameEngine () {
		this(null);
	}
	
	public GameEngine (GameData game) {
		init(game);
	}

	@Override
	public void init(GameData gameData) {
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
	public void load(GameData game) {
		 runnableGame.load(game);
	}

	@Override
	public void play () throws EngineException {
		runnableGame.run();
	}
}