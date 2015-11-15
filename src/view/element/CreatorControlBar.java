package view.element;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.screen.AbstractScreen;
import view.screen.StartScreen;

public class CreatorControlBar extends ControlBar {

	private AbstractScreen currentScreen;
	private Workspace workspace;

	public CreatorControlBar(GridPane pane, AbstractScreen screen, Workspace workspace) {
		super(pane);
		this.currentScreen = screen;
		this.workspace = workspace;
		makePane();
	}

	@Override
	protected void makePane() {
		VBox box = new VBox();
		box.minWidthProperty().bind(currentScreen.getScene().widthProperty());
		MenuBar mainMenu = new MenuBar();
		makeMenuBar(mainMenu);
		ToolBar toolBar = new ToolBar();
		makeTools(toolBar);
		box.getChildren().add(mainMenu);
		box.getChildren().add(toolBar);
		pane.add(box, 0, 0);
	}

	private void makeTools(ToolBar toolBar) {
		Button backButton = makeButton("back", e -> currentScreen.setNextScreen(new StartScreen()));
		Button addButton = makeButton("add", e -> workspace.addLevel());
		toolBar.getItems().addAll(backButton, addButton);
	}

	private void makeMenuBar(MenuBar mainMenu) {
		MenuItem load = makeMenuItem("Load Game", null);
		MenuItem save = makeMenuItem("Save Game", null);
		Menu file = makeMenu("File", load, save);

		MenuItem addLevel = makeMenuItem("Add New Level", e -> workspace.addLevel());
		Menu edit = makeMenu("Edit", addLevel);

		MenuItem toolbar = new MenuItem("Toolbar");
		// https://docs.oracle.com/javafx/2/api/javafx/scene/control/CheckMenuItem.html
		Menu window = makeMenu("Windows", toolbar);

		MenuItem doc = new MenuItem("Documentation");
		Menu help = makeMenu("Help", doc);

		mainMenu.getMenus().addAll(file, edit, window, help);
	}

}
