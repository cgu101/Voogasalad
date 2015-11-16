package data.saver;

import java.util.ArrayList;
import java.util.List;

import authoring.LevelOne;
import authoring.model.bundles.Bundle;
import authoring.model.level.ALevel;
import authoring.model.level.ILevel;
import data.IFileManager;
import data.examples.Spaceship;
import data.model.GameData;
import data.model.LevelData;
import data.model.StateData;

public class SaveMaster implements IFileManager{
	private GameSaver myGameSaver;
	private LevelSaver myLevelSaver;

	public SaveMaster () {
		this.myGameSaver = new GameSaver();
		this.myLevelSaver = new LevelSaver();
	}

	public static void main (String[] args) {
		GameSaver q = new GameSaver();
		Spaceship s = new Spaceship();
		System.out.println("saving");
		ILevel level = new LevelOne();
		level.addActor(s);
//		List<LevelOne> levelList = new ArrayList<LevelOne>();
//		Bundle<ALevel> levelBundle = null;
//		levelList.add(level);
//		GameData game = new GameData(new StateData(), new LevelData(levelList));
		GameData game = new GameData(new StateData(), new LevelData(null));
		q.saveGame(game, "src/DESIGN/datafiles/game.xml");
		
//		q.save(s);
	}

	@Override
	public void saveGame(GameData game, String fileName) {
		// TODO Auto-generated method stub
//		myGameSaver.saveGame(game, fileName));
	}

	@Override
	public GameData loadGame(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveState(StateData state, String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public StateData loadState(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void saveLevel(LevelData level, String fileName) {
		// TODO Auto-generated method stub

	}


	@Override
	public LevelData loadLevel(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
