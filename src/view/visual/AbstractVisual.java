package view.visual;

import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class AbstractVisual {
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private String className = this.getClass().getName();
	protected ResourceBundle myResources;
	protected ResourceBundle visualResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "Visual");

	protected Font titleFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("Ubuntu.ttf"),
			Double.parseDouble(visualResources.getString("title")));
	protected Font headerFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("Ubuntu.ttf"),
			Double.parseDouble(visualResources.getString("header")));
	protected Font textFont = Font.loadFont(getClass().getClassLoader().getResourceAsStream("Ubuntu.ttf"),
			Double.parseDouble(visualResources.getString("text")));

	protected void findResources() {
		myResources = ResourceBundle
				.getBundle(DEFAULT_RESOURCE_PACKAGE + className.substring(className.lastIndexOf('.') + 1));
	}
	
	protected void showError(String title, String message) {
		Alert uhoh = new Alert(AlertType.ERROR);
		makeAlert(title, message, uhoh);
	}
	
	protected void showWarning(String title, String message) {
		Alert uhoh = new Alert(AlertType.WARNING);
		makeAlert(title, message, uhoh);
	}
	
	protected void showConfirmation(String title, String message) {
		Alert uhoh = new Alert(AlertType.CONFIRMATION);
		makeAlert(title, message, uhoh);
	}

	private void makeAlert(String title, String message, Alert uhoh) {
		uhoh.setTitle(title);
		uhoh.setContentText(message);
		uhoh.show();
	}
}