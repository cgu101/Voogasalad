package view.level;

import authoring.controller.AuthoringController;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;

public interface LevelInterface {
	public Tab getTab();

	public AuthoringController getController();

	public String makeTitle(int i);

	public void updateBackground(Image backgroundImage);
	
	public String getTitle ();
}
