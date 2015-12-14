package authoring.model.game;

import java.util.Observable;
import java.util.Observer;

import authoring.model.actors.ActionMail;
import authoring.model.actors.ActionType;
import authoring.model.properties.Property;
import engine.GameEngine;
import exceptions.EngineException;

/**
 * @author Austin
 */
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
		case NEXT_LEVEL:
			this.changeLevel();
			break;
		default:
			break;
		
		}
	}
	
	public boolean shouldChange () {
		return this.stateChange;
	}
	
	public void changeLevel () {
		try {
			gameEngine.nextLevel();
		} catch (EngineException e) {
			gameEngine.displayError(e.getMessage());
		}
	}
	
}
