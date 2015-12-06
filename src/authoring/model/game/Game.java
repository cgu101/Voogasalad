package authoring.model.game;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import authoring.model.bundles.Bundle;
import authoring.model.level.Level;
import authoring.model.properties.Property;

/**
 * 
 * @author Austin
 *
 */

public class Game implements Serializable {
	/**
	 * Generated Serializable ID 
	 */
	private static final long serialVersionUID = 1433330690305157644L;
	
	private Bundle<Level> myLevelBundle;
	private Bundle<Property<?>> myPropertyBundle;
	
	public Game () {
		myLevelBundle = new Bundle<Level>();
		myPropertyBundle = new Bundle<Property<?>>();
	}
	
	public void addLevel (Level level) {
		myLevelBundle.add(level);
	}
	
	public void addLevel () {
		int size = myLevelBundle.getSize();
		myLevelBundle.add(new Level(Integer.toString(size)));
	}
	
	public void addAllLevels (Bundle<Level> bundle) {
		for (Level l : bundle) {
			myLevelBundle.add(l);
		}
	}
	
	public Level getLevel (String levelName) {
		return myLevelBundle.get(levelName);
	}

	public void addProperty (Property<?> property) {
		myPropertyBundle.add(property);
	}
	
	public void addAllProperties (Bundle<Property<?>> bundle) {
		for (Property<?> p : bundle) {
			myPropertyBundle.add(p);
		}
	}
	
	public Property<?> getProperty (String propertyName) {
		return myPropertyBundle.get(propertyName);
	}
	
	public Bundle<Property<?>> getProperties(){
		return myPropertyBundle;
	}
	
	public Bundle<Level> getBundleLevels () {
		return myLevelBundle;
	}
	
	public Collection<Level> getLevels () {
		Map<String, Level> myLevelMap = myLevelBundle.getComponents();
		Collection<Level> myLevels = myLevelMap.values();
		return myLevels;
	}
}
