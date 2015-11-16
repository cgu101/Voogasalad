package data.model;

import java.util.Arrays;
import java.util.List;

import authoring.model.level.ALevel;

public class LevelData {
	private List<ALevel> levels;
	private int numberOfLevels;
	
	public LevelData(List<ALevel> levelList) {
		this.levels = levelList;
		updateNumberOfLevels();
	}

	private void updateNumberOfLevels () {
		numberOfLevels = levels.size();
	}
	
	public void add (ALevel... levelList) {
		levels.addAll(Arrays.asList(levelList));
	}
	
	public void remove (ALevel... levelList) {
		levels.removeAll(Arrays.asList(levelList));
	}
	
	public List<ALevel> getLevels () {
		return levels;
	}
}
