package util;

import java.io.File;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileChooserUtility {
	
	private static final String LOAD_MESSAGE = "File Loader Window";
	private static final String SAVE_MESSAGE = "File Saver Window";
	private static final String DEFAULT_DIRECTORY = ".";
	
	public static File load (Stage stage) {
		FileChooser fileChooser = initializeFileChooser(LOAD_MESSAGE, DEFAULT_DIRECTORY);
		File file = fileChooser.showOpenDialog(stage);
		return file;
	}
	
	public static File save (Stage stage) {
		FileChooser fileChooser = initializeFileChooser(SAVE_MESSAGE, DEFAULT_DIRECTORY);
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		File saveFile = fileChooser.showSaveDialog(stage);
		System.out.println(saveFile.getAbsolutePath()); //TODO Remove this test
		
		return saveFile;
	}
	
	private static FileChooser initializeFileChooser (String title, String initialDirectory) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(initialDirectory));
		return fileChooser;
	}
}
