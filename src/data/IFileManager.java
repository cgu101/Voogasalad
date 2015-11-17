package data;

import java.io.File;

import authoring.model.game.Game;
import authoring.model.level.Level;
import engine.State;
import exceptions.data.GameFileException;

/*
 *  Interface for the file manager.
 *  Used by the authoring environment to save/load entire games/levels.
 *  Used by the game player to save/load progress midgame.
 */

public interface IFileManager {
	public void saveGame(Game game, String fileName) throws GameFileException;
	public Game loadGame(String fileName) throws GameFileException;
	public Game loadGame(File file) throws GameFileException;

	public void saveLevel(Level level, String fileName) throws GameFileException;
	public Level loadLevel(String fileName) throws GameFileException;
	public Level loadLevel(File file) throws GameFileException;
	
	public void saveState(State state, String fileName) throws GameFileException;
	public State loadState(String fileName) throws GameFileException;
	public State loadState(File file) throws GameFileException;
	
	public Game testLoadGame (File file) throws GameFileException;
	public void testSaveGame(Game game, File file) throws GameFileException;
}
