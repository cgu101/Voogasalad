package view.element;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.screen.AbstractScreen;
import view.screen.StartScreen;

public class ControlBarGeneral extends ControlBar {
	private AbstractScreen currentScreen;
	private double width;

	public ControlBarGeneral(GridPane pane, AbstractScreen screen, double width) {
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
		Button backButton = makeButton("back");
		backButton.setOnAction(e -> currentScreen.setNextScreen(new StartScreen()));
		toolBar.getItems().addAll(backButton);
	}
}
