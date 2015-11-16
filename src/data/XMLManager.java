package data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import authoring.model.game.Game;
import authoring.model.level.Level;
import exceptions.data.GameFileException;

/*
 *  EXAMPLE CODE FOR SAVING AND LOADING 
 */


public class XMLManager implements IFileManager{
	private static final String DEFAULT_FILE_FOLDER = "src/resources/datafiles/";
	private static final String DEFAULT_GAME_LIBRARY_FOLDER = DEFAULT_FILE_FOLDER + "games/";
	private static final String DEFAULT_SAVESTATE_FOLDER = DEFAULT_FILE_FOLDER + "saves/";
	
	private void saveFile (Object obj, String filePath) throws GameFileException {
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
	
	private Object loadFile (String filePath) throws GameFileException {
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

	@Override
	public void saveGame(Game game, String fileName) throws GameFileException {
		
		// save to game.xml file
		// filepath = "src/DESIGN/datafiles/game.xml";
		saveFile(game, DEFAULT_GAME_LIBRARY_FOLDER + fileName);
		
		
	}

	@Override
	public Game loadGame(String fileName) throws GameFileException {

		// load from game.xml
		try {
			return (Game) loadFile(DEFAULT_GAME_LIBRARY_FOLDER + fileName);
		} catch (Exception e) {
			throw new GameFileException();
		}
		
	}

	@Override
	public void saveLevel(Level level, String fileName) throws GameFileException {
		// TODO Auto-generated method stub
		saveFile(level, DEFAULT_GAME_LIBRARY_FOLDER + fileName);
	}

	@Override
	public Level loadLevel(String fileName) throws GameFileException {
		// TODO Auto-generated method stub
		try {
			return (Level) loadFile(DEFAULT_GAME_LIBRARY_FOLDER + fileName);
		} catch (Exception e) {
			throw new GameFileException();
		}
	}

//	@Override
//	public void saveState(StateData state, String fileName) throws GameFileException {
//		// TODO Auto-generated method stub
//		saveFile(state, DEFAULT_SAVESTATE_FOLDER + fileName);
//	}
//
//	@Override
//	public StateData loadState(String fileName) throws GameFileException {
//		try {
//			return (StateData) loadFile(DEFAULT_SAVESTATE_FOLDER + fileName);
//		} catch (Exception e) {
//			throw new GameFileException();
//		}
//	}

}
