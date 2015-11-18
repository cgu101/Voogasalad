package view.element;

import authoring.controller.AuthoringController;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import view.screen.AbstractScreen;

public class LevelMap extends Map {
	private Tab myTab;
	private ScrollPane sp;
	private AuthoringController controller;


	public LevelMap(GridPane pane, int i, AbstractScreen screen) {
		super(pane, screen);
		myTab = new Tab("Level " + (i + 1));
		myTab.setContent(pane);
		myTab.setId(Integer.toString(i));	
		controller = new AuthoringController();
	}

	public Tab getTab() {
		return myTab;
	}
	
	public AuthoringController getController() {
		return controller;
	}
	
//	public LevelConstructor getLevelConstructor () {
//		return constructor;
//	}
	
}
