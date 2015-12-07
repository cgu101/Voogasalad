package authoring.model.game;

import java.util.Observable;
import java.util.Observer;

import authoring.model.actors.ActionType;
import engine.GameEngine;
import exceptions.EngineException;

public class ActorDependencyInjector implements Observer {

	public GameEngine gameEngine;
	
	public ActorDependencyInjector (GameEngine engine) {
		this.gameEngine = engine;
	}
	
	public void hookRelation (Observable o) {
		o.addObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		ActionType a = (ActionType) arg1;
		
		switch (a) {
		case END_GAME:
			//TODO
			break;
		case NEXT_LEVEL:
			changeLevel();
			break;
		case PREVIOUS_LEVEL:
			//TODO
			break;
		case WIN_GAME:
			//TODO
			break;
		default:
			break;
		
		}
	}
	
	private void changeLevel () {
		try {
			gameEngine.nextLevel();
		} catch (EngineException e) {
			gameEngine.displayError(e.getMessage());
		}
	}
	
}
