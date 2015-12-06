package view.level;

import authoring.controller.AuthoringController;
import authoring.model.Anscestral;
import authoring.model.level.Level;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;

/**
 * 
 * @author Austin
 */
public interface LevelInterface extends Anscestral {
	public Tab getTab();

	public AuthoringController getController();

	public void updateBackground(Image backgroundImage);

	public String getTitle();

	public void redraw(Level modelLevel);

	public Level buildLevel();
	
	public LevelType getType();
}
