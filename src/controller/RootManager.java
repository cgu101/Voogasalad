package controller;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.screen.AbstractScreen;
import view.screen.StartScreen;

/**
 * 
 * @author Sung-Hoon and Austin
 *
 */

public class RootManager implements Observer {

	private Stage stage;
	private AController currentController;

	public RootManager(Stage s) {
		stage = s;
		AbstractScreen startScreen = new StartScreen();
		currentController = new MenuController(stage, startScreen);
		stage.setScene(startScreen.getScene());
		stage.show();
		stage.setResizable(startScreen.isResizable());
		stage.maximizedProperty().addListener(e -> maxOrMin(stage.getScene()));
	}

	private void maxOrMin(Scene scene) {
		// TODO: will work only if we have some way to access the current screen on the stage &
		// that screen's isMaximized() property.
	}

	public void run() {
		try {
			currentController.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// TODO gui stuff
		}
	}

	@Override
	public void update(Observable arg0, Object controller) {
		// TODO Auto-generated method stub
		currentController = (AController) controller;
	}

}
