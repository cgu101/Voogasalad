package data.model;

public class GameData {
	private StateData stateData;
	private LevelData levelData;
	
	public GameData (StateData stateData, LevelData levelData) {
		this.stateData = stateData;
		this.levelData = levelData;
	}
	public StateData getStateData() {
		return stateData;
	}
	public LevelData getLevelData() {
		return levelData;
	}
}
