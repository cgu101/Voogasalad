package util;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileChooserUtility {
	
	private static final String LOAD_MESSAGE = "File Loader Window";
	private static final String SAVE_MESSAGE = "File Saver Window";
	private static final String DEFAULT_DIRECTORY = ".";
	private static final String DEFAULT_FILE_PROMPT  = "XML files (*.xml)";
	private static final String DEFAULT_FILE_FORMAT = "*xml";
	
	public static File load (Window stage) {
		FileChooser fileChooser = initializeFileChooser(LOAD_MESSAGE, DEFAULT_DIRECTORY);
		File file = fileChooser.showOpenDialog(stage);
		return file;
	}
	
	public static File save (Window stage) {
		FileChooser fileChooser = initializeFileChooser(SAVE_MESSAGE, DEFAULT_DIRECTORY);
		
		FileChooser.ExtensionFilter extFilter = 
				new FileChooser.ExtensionFilter(DEFAULT_FILE_PROMPT, DEFAULT_FILE_FORMAT);
		fileChooser.getExtensionFilters().add(extFilter);
		
		File saveFile = fileChooser.showSaveDialog(stage);
		return saveFile;
	}
	
	private static FileChooser initializeFileChooser (String title, String initialDirectory) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(initialDirectory));
		return fileChooser;
	}
}
