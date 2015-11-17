package view.controlbar;

import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.screen.AbstractScreen;
import view.screen.PlayerScreen;
import view.screen.StartScreen;

public class ControlBarPlayer extends ControlBar {
	private PlayerScreen currentScreen;
	private double width;

	public ControlBarPlayer(GridPane pane, PlayerScreen screen, double width) {
		super(pane);
		this.currentScreen = screen;
		this.width = width;
		makePane();
	}

	@Override
	protected void makePane() {
		VBox box = new VBox();
		box.setPrefWidth(width);
		MenuBar mainMenu = new MenuBar();
		createMenuBar(mainMenu);
		box.getChildren().add(mainMenu);
		
		ToolBar toolBar = new ToolBar();
		makeTools(toolBar);
		box.getChildren().add(toolBar);
		pane.add(box, 0, 0);

	}

	private void createMenuBar(MenuBar mainMenu) {
		MenuItem load = makeMenuItem(myResources.getString("load"), e -> currentScreen.loadGame());
		Menu file = addToMenu(new Menu(myResources.getString("file")), load);

		CheckMenuItem fullscreen = new CheckMenuItem(myResources.getString("fullscreen"));
		fullscreen.selectedProperty().bindBidirectional(currentScreen.getFullscreenProperty());
		Menu window = addToMenu(new Menu(myResources.getString("window")), fullscreen);
		makeMenuBar(mainMenu, file, window);
	}
	
	private void makeTools(ToolBar toolBar) {
		Button backButton = makeButton("back", e -> currentScreen.setNextScreen(new StartScreen()));
		Button playButton = makeButton("play", e -> {}); //TODO
		Button pauseButton = makeButton("pause", e -> {}); //TODO
		Button saveButton = makeButton("save", e -> {}); //TODO
		toolBar.getItems().addAll(backButton, playButton, pauseButton, saveButton);
	}
}
