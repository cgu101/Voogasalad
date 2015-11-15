package controller;

import java.util.Observable;

import javafx.stage.Stage;
import view.screen.AbstractScreen;

/**
 * 
 * @author Sung-Hoon and Austin
 *
 */

public abstract class AController extends Observable {
	protected AbstractScreen currentScreen;
	protected Stage mainStage;

	public AController(Stage stage) {
		// TODO: change this
		mainStage = stage;
		currentScreen = null;
	}

	public AController(Stage stage, AbstractScreen screen) {
		mainStage = stage;
		currentScreen = screen;
	}

	public abstract void run() throws Exception;

	public void play() throws Exception {
		run();
		updateScreen();
	}

	protected void updateScreen() {
		if (currentScreen.getNextScreen() != null) {
			currentScreen = currentScreen.getNextScreen();
			mainStage.setScene(currentScreen.getScene());
			mainStage.setTitle(currentScreen.getTitle());
			mainStage.setResizable(currentScreen.isResizable());
			mainStage.show();
		}
	}

	protected void switchController(AController controller) {
		setChanged();
		notifyObservers(controller);
	}

}
