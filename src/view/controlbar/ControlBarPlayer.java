package view.controlbar;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resources.KeyCodesMap;
import view.element.AbstractDockElement;
import view.screen.AbstractScreen;
import view.screen.PlayerScreen;
import view.screen.StartScreen;

public class ControlBarPlayer extends ControlBar {
	private PlayerScreen currentScreen;
	private double width;
	private VBox box;
	private ToolBar toolBar;
	Menu hideAndShow;

	public ControlBarPlayer(GridPane pane, PlayerScreen screen, double width) {
		super(pane);
		this.currentScreen = screen;
		this.width = width;
		makePane();
	}

	@Override
	protected void makePane() {
		box = new VBox();
		box.setPrefWidth(width);
		MenuBar mainMenu = new MenuBar();
		createMenuBar(mainMenu);
		box.getChildren().add(mainMenu);
		
		toolBar = new ToolBar();
		makeTools();
		box.getChildren().add(toolBar);
		pane.add(box, 0, 0);

	}

	private void createMenuBar(MenuBar mainMenu) {
		MenuItem load = makeMenuItem(myResources.getString("loadGame"), e -> currentScreen.loadGame(), KeyCode.L,
				KeyCombination.CONTROL_DOWN);
		MenuItem save = makeMenuItem(myResources.getString("saveGame"), e -> currentScreen.saveState(), KeyCode.S,
				KeyCombination.CONTROL_DOWN);
		Menu file = addToMenu(new Menu(myResources.getString("file")), load, save);

		CheckMenuItem fullscreen = makeCheckMenuItem(myResources.getString("fullscreen"), KeyCode.F,
				KeyCombination.CONTROL_DOWN);
		fullscreen.selectedProperty().bindBidirectional(currentScreen.getFullscreenProperty());
		
		CheckMenuItem toolbar = new CheckMenuItem(myResources.getString("toolbar"));
		toolbar.selectedProperty().setValue(true);
		toolbar.selectedProperty().addListener(e -> toggleToolbar(toolbar.selectedProperty().getValue()));
		
		CheckMenuItem highScore = new CheckMenuItem(myResources.getString("highscore"));
		highScore.selectedProperty().setValue(false);
		highScore.selectedProperty().addListener(e -> System.out.println("High Score not implemented yet"));
		
		hideAndShow = addToMenu(new Menu(myResources.getString("hideshow")), toolbar, highScore);
		
		Menu window = addToMenu(new Menu(myResources.getString("window")), fullscreen, hideAndShow);
		makeMenuBar(mainMenu, file, window);
	}
	
	private void makeTools() {
		Button backButton = makeButton("back", e -> currentScreen.setNextScreen(new StartScreen()));
		Button playButton = makeButton("play", e -> currentScreen.resume());
		Button pauseButton = makeButton("pause", e -> currentScreen.pause());
		Button saveButton = makeButton("save", e -> currentScreen.saveState()); //TODO
		Button loadButton = makeButton("load", e -> currentScreen.loadState()); // TODO
		Button replayButton = makeButton("replay", e -> currentScreen.confirmRestartOrReplay("Replay Level"));
		Button resetButton = makeButton("reset", e -> currentScreen.confirmRestartOrReplay("Reset Game"));
		toolBar.getItems().addAll(backButton, playButton, pauseButton, saveButton, loadButton, replayButton, resetButton);
	}
	
	private void toggleToolbar(Boolean value) {
		if (value) {
			box.getChildren().add(toolBar);
		} else {
			box.getChildren().remove(toolBar);
		}
	}

	/**
	 * This method initializes the different components of the 
	 * Player that are relevant only once a game has been loaded
	 * i.e. ActorMonitor and HUD
	 */
	public void initializeComponents() {
		if(currentScreen.getComponents() != null){
			for (AbstractDockElement c : currentScreen.getComponents()) {
				CheckMenuItem item = new CheckMenuItem(c.getClass().getSimpleName());
				item.selectedProperty().bindBidirectional(c.getShowingProperty());
				item.selectedProperty().set(false);
				addToMenu(hideAndShow, item);
			}
		}
		
	}
}
