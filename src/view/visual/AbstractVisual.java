package view.visual;

import java.util.ResourceBundle;

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
}