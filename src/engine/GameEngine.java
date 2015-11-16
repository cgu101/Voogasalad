package engine;

import java.util.ArrayList;
import java.util.List;

import data.model.GameData;
import engine.runnable.RunnableGame;
import exceptions.EngineException;

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