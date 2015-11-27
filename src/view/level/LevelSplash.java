package view.level;

import authoring.controller.AuthoringController;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import view.element.AbstractElement;
import view.screen.AbstractScreen;

public class LevelSplash extends AbstractElement implements LevelInterface {
	private Tab myTab;

	public LevelSplash(GridPane pane, int i, AbstractScreen screen) {
		super(pane);
		myTab = new Tab("Level " + (i + 1));
		myTab.setContent(pane);
		myTab.setId(Integer.toString(i));
	}

	@Override
	public Tab getTab() {
		return myTab;
	}

	@Override
	public AuthoringController getController() {
		return null;
	}

	@Override
	protected void makePane() {
		// TODO Auto-generated method stub

	}

}
