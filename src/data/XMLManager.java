package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import authoring.model.game.Game;
import authoring.model.level.Level;
import engine.State;
import exceptions.data.GameFileException;


public class XMLManager {
	
	private static void saveFile (Object obj, String filePath) throws GameFileException {
		XStream mySerializer = new XStream(new DomDriver());
		FileOutputStream fos = null;
		try{            
			String xml = mySerializer.toXML(obj);
			fos = new FileOutputStream(filePath);
			fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
			byte[] bytes = xml.getBytes("UTF-8");
			fos.write(bytes);
			System.out.println("what?");
		}catch (Exception e){
			e.printStackTrace();
			System.err.println("Error in XML Write: " + e.getMessage());
		}
		finally{
			if(fos != null){
				try{
					fos.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("DEBUG");
		}
	}
	
	private static Object loadFile (String filePath) throws GameFileException {
		XStream mySerializer = new XStream(new DomDriver());
		FileInputStream fis = null;
		try {
			fis = new FileInputStream (filePath);
			return mySerializer.fromXML(fis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(fis != null){
				try{
					fis.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		throw new GameFileException();
	}
	/**
	 * Saves a game to an absolute file location.
	 * @param game A {@link Game} containing a bundle of metadata properties and a bundle of levels.
	 * @param fileLocation
	 * @throws GameFileException
	 */
	public static void saveGame(Game game, String fileLocation) throws GameFileException {
		
		// save to game.xml file
		// filepath = "src/DESIGN/datafiles/game.xml";
		saveFile(game, fileLocation);
		
		
	}
	
	/**
	 * Core method to test a game object based on its absolute file location
	 * TODO: Remove this method for final production
	 * 
	 * @param game
	 * @param fileLocation
	 * @throws GameFileException
	 */
	public static void testSaveGame (Game obj, File file) throws GameFileException {
		String filePath = file.getAbsolutePath();
		
		XStream mySerializer = new XStream(new DomDriver());
		FileOutputStream fos = null;
		try{            
			String xml = mySerializer.toXML(obj);
			fos = new FileOutputStream(filePath);
			fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
			byte[] bytes = xml.getBytes("UTF-8");
			fos.write(bytes);
		}catch (Exception e){
			System.err.println("Error in XML Write: " + e.getMessage());
		}
		finally{
			if(fos != null){
				try{
					fos.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		throw new GameFileException();
	}

	/**
	 * Core method to test loading a game based on a file to return a Game object
	 * TODO: Eventually remove this method for production
	 * 
	 * @param file
	 * @return Game Object
	 * @throws GameFileException
	 */
	public static Game testLoadGame (File file) throws GameFileException {
		String fileLocation = file.getName();
		
		try {
			System.out.println(fileLocation);
			return null;
		} catch (Exception e) {
			throw new GameFileException();
		}
	}

	/**
	 * Loads a game from an absolute file path.
	 * @param fileLocation
	 * @return A {@link Game} to be loaded into the engine.
	 * @throws GameFileException
	 */
	public static Game loadGame(String fileLocation) throws GameFileException {

		// load from game.xml
		try {
			return (Game) loadFile(fileLocation);
		} catch (Exception e) {
			throw new GameFileException();
		}
		
	}
	/**
	 * Saves a single level to an absolute file location.
	 * @param level
	 * @param fileLocation
	 * @throws GameFileException
	 */
	public static void saveLevel(Level level, String fileLocation) throws GameFileException {
		// TODO Auto-generated method stub
		saveFile(level, fileLocation);
	}
	
	/**
	 * Loads a single level from an absolute file path.
	 * @param fileLocation
	 * @return
	 * @throws GameFileException
	 */
	public static Level loadLevel(String fileLocation) throws GameFileException {
		// TODO Auto-generated method stub
		try {
			return (Level) loadFile(fileLocation);
		} catch (Exception e) {
			throw new GameFileException();
		}
	}

	/**
	 * Saves a save state to an absolute file location.
	 * @param state
	 * @param filePath
	 * @throws GameFileException
	 */
	public static void saveState(State state, String filePath) throws GameFileException {
		// TODO Auto-generated method stub
		saveFile(state, filePath);
//		saveFile(state, DEFAULT_SAVESTATE_FOLDER + filePath);
	}

	/**
	 * Loads a save state from an absolute file path.
	 * @param filePath
	 * @return
	 * @throws GameFileException
	 */
	public static State loadState(String filePath) throws GameFileException {
		try {
			return (State) loadFile(filePath);
//			return (State) loadFile(DEFAULT_SAVESTATE_FOLDER + filePath);
		} catch (Exception e) {
			throw new GameFileException();
		}
	}
	/**
	 * Loads a game from a file.
	 * @param file
	 * @return
	 * @throws GameFileException
	 */
	public static Game loadGame(File file) throws GameFileException {
		return loadGame(file.getName());
	}
	/**
	 * Loads a single level from a file.
	 * @param file
	 * @return
	 * @throws GameFileException
	 */
	public static Level loadLevel(File file) throws GameFileException {
		return loadLevel(file.getName());
	}
	/**
	 * Loads a savestate from a file.
	 * @param file
	 * @return
	 * @throws GameFileException
	 */
	public static State loadState(File file) throws GameFileException {
		return loadState(file.getName());
	}

}
