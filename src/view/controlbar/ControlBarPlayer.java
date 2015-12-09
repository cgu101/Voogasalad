package view.controlbar;

import java.util.Optional;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.element.AbstractDockElement;
import view.screen.PlayerScreen;
import view.screen.StartScreen;

public class ControlBarPlayer extends ControlBar {
	private PlayerScreen screen;
	private double width;
	private VBox box;
	private ToolBar toolBar;
	private Menu hideAndShow;

	public ControlBarPlayer(GridPane pane, PlayerScreen screen, double width) {
		super(pane);
		this.screen = screen;
		this.width = width;
		makePane();
	}

	@Override
	protected void makePane() {
		box = new VBox();
		box.minWidthProperty().bind(screen.getScene().widthProperty());
		MenuBar mainMenu = new MenuBar();
		createMenuBar(mainMenu);
		box.getChildren().add(mainMenu);
		
		toolBar = new ToolBar();
		makeTools();
		box.getChildren().add(toolBar);
		pane.add(box, 0, 0);

	}

	private void createMenuBar(MenuBar mainMenu) {
		MenuItem load = makeMenuItem(myResources.getString("loadGame"), e -> screen.loadGame(), KeyCode.L,
				KeyCombination.CONTROL_DOWN);
		MenuItem save = makeMenuItem(myResources.getString("saveGame"), e -> screen.saveState(), KeyCode.S,
				KeyCombination.CONTROL_DOWN);
		Menu file = addToMenu(new Menu(myResources.getString("file")), load, save);

		CheckMenuItem fullscreen = makeCheckMenuItem(myResources.getString("fullscreen"), KeyCode.F,
				KeyCombination.CONTROL_DOWN);
		fullscreen.selectedProperty().bindBidirectional(screen.getFullscreenProperty());
		
		CheckMenuItem toolbar = new CheckMenuItem(myResources.getString("toolbar"));
		toolbar.selectedProperty().setValue(true);
		toolbar.selectedProperty().addListener(e -> toggleToolbar(toolbar.selectedProperty().getValue()));
		
		CheckMenuItem highScore = new CheckMenuItem(myResources.getString("highscore"));
		highScore.selectedProperty().setValue(false);
		highScore.selectedProperty().addListener(e -> System.out.println("High Score not implemented yet"));
		
		hideAndShow = addToMenu(new Menu(myResources.getString("hideshow")), toolbar, highScore);
		
		hideAndShow = addToMenu(new Menu(myResources.getString("hideshow")), toolbar);
		makeComponentCheckMenus(hideAndShow, screen);
		Menu window = addToMenu(new Menu(myResources.getString("window")), fullscreen, hideAndShow);
		makeMenuBar(mainMenu, file, window);
	}
	
	private void makeTools() {
		Button backButton = makeButton("back", e -> screen.setNextScreen(new StartScreen()));
		Button playButton = makeButton("play", e -> screen.resume());
		Button pauseButton = makeButton("pause", e -> screen.pause());
		Button saveButton = makeButton("save", e -> screen.saveState()); //TODO
		Button loadButton = makeButton("load", e -> screen.loadState()); // TODO
		Button replayButton = makeButton("replay", e -> screen.confirmRestartOrReplay("Replay Level"));
		Button resetButton = makeButton("reset", e -> screen.confirmRestartOrReplay("Reset Game"));
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
		if(screen.getComponents() != null){
			for (AbstractDockElement c : screen.getComponents()) {
				CheckMenuItem item = new CheckMenuItem(myResources.getString(c.getClass().getSimpleName()));
				item.selectedProperty().bindBidirectional(c.getShowingProperty());
				item.selectedProperty().set(false);
				addToMenu(hideAndShow, item);
			}
		}
		
	}
}
