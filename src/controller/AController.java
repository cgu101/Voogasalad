package controller;

import java.util.Observable;

import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import view.element.AbstractDockElement;
import view.screen.AbstractScreen;

/**
 * The controller that handles displaying and swapping out GUI screens.
 * @author Sung-Hoon and Austin
 *
 */

public abstract class AController extends Observable {
	protected AbstractScreen currentScreen;
	protected Stage mainStage;
	
	public AController() {
		
	}

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
			currentScreen.getFullscreenProperty()
					.addListener(e -> toggleScreen(currentScreen.getFullscreenProperty().getValue()));
			mainStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			mainStage.show();
		}
	}

	private void toggleScreen(Boolean value) {
		mainStage.setFullScreen(value);
		if (value) {
			for (AbstractDockElement c : currentScreen.getComponents()) {
				c.getShowingProperty().setValue(false);
				c.getShowingProperty().setValue(true);
			}
		}
	}

	protected void switchController(AController controller) {
		setChanged();
		notifyObservers(controller);
	}

}
