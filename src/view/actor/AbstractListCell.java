package view.actor;

import java.util.ResourceBundle;

import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

public abstract class AbstractListCell extends ListCell<String> {
	/**
	 * @author David
	 * 
	 * This class is designed to be extended to create modified ListCells.
	 * It contains info for getting resource files and fonts.
	 * Implement the makeCell method to decide how a cell should be created.
	 * 
	 */
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

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else if (item != null) {
			makeCell(item);
		}
	}

	protected abstract void makeCell(String item);
}
