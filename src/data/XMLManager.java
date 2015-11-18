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
	
	public void testSaveGame (Game obj, File file) throws GameFileException {
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

	public Game testLoadGame (File file) throws GameFileException {
		String fileName = file.getName();
		
		try {
			System.out.println(DEFAULT_GAME_LIBRARY_FOLDER + fileName);
			return null;
		} catch (Exception e) {
			throw new GameFileException();
		}
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

	@Override
	public void saveState(State state, String filePath) throws GameFileException {
		// TODO Auto-generated method stub
		saveFile(state, filePath);
//		saveFile(state, DEFAULT_SAVESTATE_FOLDER + filePath);
	}

	@Override
	public State loadState(String filePath) throws GameFileException {
		try {
			return (State) loadFile(filePath);
//			return (State) loadFile(DEFAULT_SAVESTATE_FOLDER + filePath);
		} catch (Exception e) {
			throw new GameFileException();
		}
	}

	@Override
	public Game loadGame(File file) throws GameFileException {
		return loadGame(file.getName());
	}

	@Override
	public Level loadLevel(File file) throws GameFileException {
		return loadLevel(file.getName());
	}

	@Override
	public State loadState(File file) throws GameFileException {
		return loadState(file.getName());
	}

}
