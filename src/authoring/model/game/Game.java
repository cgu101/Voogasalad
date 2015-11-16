package authoring.model.game;

import java.util.Observable;

import authoring.model.bundles.Bundle;
import authoring.model.level.Level;
import authoring.model.properties.Property;

public class Game extends Observable {
	private Bundle<Level> myLevelBundle;
	private Level currentLevel;
	private Bundle<Property<?>> myPropertyBundle;
	
	public Game () {
		myLevelBundle = new Bundle<Level>();
	}
	
	public void addLevel (Level level) {
		myLevelBundle.add(level);
	}
	
	public void setCurrentLevel (String uniqueID) {
		Level newLevel = myLevelBundle.get(uniqueID);
		if (newLevel != null) {
			currentLevel = newLevel;
			update();
		}
	}
	
	public void run () {
	}
	
	public void update () {
		setChanged();
		notifyObservers(currentLevel);
	}
}
