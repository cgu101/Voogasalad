package view.level;

import authoring.controller.AuthoringController;
import authoring.model.level.Level;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;

public interface LevelInterface {
	public Tab getTab();

	public AuthoringController getController();

	public String makeTitle(int i);

	public void updateBackground(Image backgroundImage);
	
	public String getTitle ();

	public void redraw(Level modelLevel);

	public Level buildLevel();
}
