package view.level;

import authoring.controller.AuthoringController;
import javafx.scene.control.Tab;

public interface LevelInterface {
	public Tab getTab();

	public AuthoringController getController();
}
