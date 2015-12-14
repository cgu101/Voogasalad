package authoring.model.game;

import java.util.Observable;
import java.util.Observer;

import authoring.model.actors.ActionMail;
import authoring.model.actors.ActionType;
import authoring.model.properties.Property;
import engine.GameEngine;
import exceptions.EngineException;

public class ActorDependencyInjector implements Observer {

	public GameEngine gameEngine;
	public boolean stateChange;
	
	public ActorDependencyInjector (GameEngine engine) {
		this.gameEngine = engine;
		this.stateChange = false;
	}
	
	public void hookRelation (Observable o) {
		o.addObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		ActionMail a = (ActionMail) arg1;
		
		switch (a.getActionType()) {
		case END_GAME:
			//TODO
			break;
		case NEXT_LEVEL:
			//TODO
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
	
	private boolean shouldChange () {
		return this.stateChange;
	}
	
	private void changeLevel () {
		try {
			gameEngine.nextLevel();
		} catch (EngineException e) {
			gameEngine.displayError(e.getMessage());
		}
	}
	
}
