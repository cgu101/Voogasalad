package view.controlbar;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.screen.AbstractScreen;
import view.screen.StartScreen;

public class ControlBarPlayer extends ControlBar {
	private AbstractScreen currentScreen;
	private double width;

	public ControlBarPlayer(GridPane pane, AbstractScreen screen, double width) {
		super(pane);
		this.currentScreen = screen;
		this.width = width;
		makePane();
	}

	@Override
	protected void makePane() {
		VBox box = new VBox();
		box.setPrefWidth(width);
		ToolBar toolBar = new ToolBar();
		makeTools(toolBar);
		box.getChildren().add(toolBar);
		pane.add(box, 0, 0);
	}

	private void makeTools(ToolBar toolBar) {
		Button backButton = makeButton("back", e -> currentScreen.setNextScreen(new StartScreen()));
		Button playButton = makeButton("play", e -> {}); //TODO
		Button pauseButton = makeButton("pause", e -> {}); //TODO
		Button saveButton = makeButton("save", e -> {}); //TODO
		toolBar.getItems().addAll(backButton, playButton, pauseButton, saveButton);
	}
}
