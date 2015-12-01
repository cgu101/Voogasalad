package util;

import java.io.File;

import exceptions.data.GameFileException;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileChooserUtility {
	
	private static final String LOAD_MESSAGE = "File Loader Window";
	private static final String SAVE_MESSAGE = "File Saver Window";
	private static final String DEFAULT_DIRECTORY = ".";
	private static final String DEFAULT_FILE_PROMPT  = "XML files (*.xml)";
	private static final String DEFAULT_FILE_FORMAT = "*.xml";
	
	
	/**
	 * Method used for generalized load-file using JavaFX FileChooser utility
	 * 
	 * @param stage
	 * @return Loaded file corresponding to the user's choice
	 * @throws GameFileException 
	 */
	public static File load (Window stage) throws GameFileException {
		FileChooser fileChooser = initializeFileChooser(LOAD_MESSAGE, DEFAULT_DIRECTORY);
		File file = fileChooser.showOpenDialog(stage);
		
		if (!isValid(file)) { throw new GameFileException(); }
		
		return file;
	}
	
	/**
	 * Method used for generalized save-file using JavaFX FileChooser utility
	 * 
	 * @param stage
	 * @return File object for saved file corresponding to the user's choice
	 * @throws GameFileException 
	 */
	public static File save (Window stage) throws GameFileException {
		FileChooser fileChooser = initializeFileChooser(SAVE_MESSAGE, DEFAULT_DIRECTORY);
		
		FileChooser.ExtensionFilter extFilter = 
				new FileChooser.ExtensionFilter(DEFAULT_FILE_PROMPT, DEFAULT_FILE_FORMAT);
		fileChooser.getExtensionFilters().add(extFilter);
		
		File saveFile = fileChooser.showSaveDialog(stage);
		
		if (!isValid(saveFile)) { throw new GameFileException(); }
		
		return saveFile;
	}
	
	private static boolean isValid (File file) {
		if (file == null ) {
			return false;
		} else {
			return true;
		}
	}
	
	private static FileChooser initializeFileChooser (String title, String initialDirectory) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(initialDirectory));
		return fileChooser;
	}
}
