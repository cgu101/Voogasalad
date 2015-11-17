package engine.runnable;

import authoring.model.bundles.Bundle;
import authoring.model.game.Game;

public class RunnableGame implements IRunnable {

	private Bundle levelBundle;
	
	public RunnableGame (Game gameData) {
		load(gameData);
	}
	
	@Override
	public void run () {
		
	}
	
	public void reset () {
		
	}
	
	public void load (Game data) {
	}

}
