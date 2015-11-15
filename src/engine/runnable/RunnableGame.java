package engine.runnable;

import authoring.model.bundles.Bundle;
import data.model.GameData;

public class RunnableGame implements IRunnable {

	private Bundle levelBundle;
	
	public RunnableGame (GameData gameData) {
		load(gameData);
	}
	
	@Override
	public void run () {
		
	}
	
	public void reset () {
		
	}
	
	public void load (GameData data) {
	}

}
