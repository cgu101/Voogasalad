package view.controlbar;

import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
		MenuItem load = makeMenuItem(myResources.getString("loadGame"), e -> currentScreen.loadGame());
		MenuItem save = makeMenuItem(myResources.getString("saveGame"), e -> currentScreen.saveState());
		Menu file = addToMenu(new Menu(myResources.getString("file")), load, save);

		CheckMenuItem fullscreen = new CheckMenuItem(myResources.getString("fullscreen"));
		fullscreen.selectedProperty().bindBidirectional(currentScreen.getFullscreenProperty());
		
		CheckMenuItem toolbar = new CheckMenuItem(myResources.getString("toolbar"));
		toolbar.selectedProperty().setValue(true);
		toolbar.selectedProperty().addListener(e -> toggleToolbar(toolbar.selectedProperty().getValue()));
		
		CheckMenuItem highScore = new CheckMenuItem(myResources.getString("highscore"));
		highScore.selectedProperty().setValue(false);
		highScore.selectedProperty().addListener(e -> System.out.println("High Score not implemented yet"));
		
		CheckMenuItem headsUpDisplay = new CheckMenuItem(myResources.getString("hud"));
		headsUpDisplay.selectedProperty().setValue(false);
		headsUpDisplay.selectedProperty().addListener(e -> System.out.println("HeadsUpDisplay not implemented yet"));
		
		CheckMenuItem preferences = new CheckMenuItem(myResources.getString("preferences"));
		preferences.selectedProperty().setValue(false);
		preferences.selectedProperty().addListener(e -> System.out.println("Game Preferences not implemented yet"));
		
		hideAndShow = addToMenu(new Menu(myResources.getString("hideshow")), toolbar, highScore, headsUpDisplay, preferences);
		
		Menu window = addToMenu(new Menu(myResources.getString("window")), fullscreen, hideAndShow);
		makeMenuBar(mainMenu, file, window);
	}
	
	private void makeTools() {
		Button backButton = makeButton("back", e -> currentScreen.setNextScreen(new StartScreen()));
		Button playButton = makeButton("play", e -> currentScreen.resume());
		Button pauseButton = makeButton("pause", e -> currentScreen.pause());
		Button saveButton = makeButton("save", e -> currentScreen.saveState()); //TODO
		Button loadButton = makeButton("load", e -> currentScreen.loadState()); // TODO
		toolBar.getItems().addAll(backButton, playButton, pauseButton, saveButton, loadButton);
	}
	
	private void toggleToolbar(Boolean value) {
		if (value) {
			box.getChildren().add(toolBar);
		} else {
			box.getChildren().remove(toolBar);
		}
	}

	public void initializeComponents() {
		if(currentScreen.getComponents() != null){
			for (AbstractDockElement c : currentScreen.getComponents()) {
				CheckMenuItem item = new CheckMenuItem(myResources.getString(c.getClass().getSimpleName()));
				item.selectedProperty().bindBidirectional(c.getShowingProperty());
				addToMenu(hideAndShow, item);
			}
		}
		
	}
}
