package view.screen;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Scene;

public interface AbstractScreenInterface {

	abstract public void run();

	public Scene getScene();

	public AbstractScreen getNextScreen();

	public String getTitle();

	public BooleanProperty getFullscreenProperty();

}