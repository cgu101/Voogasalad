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
	private Menu toolbar;

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
		createMenuBar(mainMenu);
		ToolBar toolBar = new ToolBar();
		makeTools(toolBar);
		box.getChildren().add(mainMenu);
		box.getChildren().add(toolBar);
		pane.add(box, 0, 0);
	}

	private void makeTools(ToolBar toolBar) {
		Button backButton = makeButton("back");
		backButton.setOnAction(e -> currentScreen.setNextScreen(new StartScreen()));
		Button addButton = makeButton("add");
		addButton.setOnAction(e -> workspace.addLevel());
		toolBar.getItems().addAll(backButton, addButton);
	}

	private void createMenuBar(MenuBar mainMenu) {
		MenuItem load = makeMenuItem("Load Game", null);
		MenuItem save = makeMenuItem("Save Game", null);
		Menu file = addToMenu(new Menu("File"), load, save);

		MenuItem properties = makeMenuItem("Properties", null);
		Menu edit = addToMenu(new Menu("Edit"), properties);

//		CheckMenuItem actors = makeMenuItem("Actors List", null);
		
		toolbar = addToMenu(new Menu("Toolbar"));
		Menu window = addToMenu(new Menu("Window"), toolbar);

		MenuItem doc = new MenuItem("Documentation");
		Menu help = addToMenu(new Menu("Help"), doc);

		makeMenuBar(mainMenu, file, edit, window, help);
	}

	// enables program to add abstract dock element to view toolbar
	public void addToToolbar(MenuItem m) {
		toolbar.getItems().add(m);
	}
}
