package view.element;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.screen.AbstractScreen;
import view.screen.StartScreen;

public class CreatorControlBar extends ControlBar {

	private AbstractScreen screen;
	private Workspace workspace;
	private MenuBar mainMenu;
	private ToolBar toolBar;
	private VBox box;

	public CreatorControlBar(GridPane pane, AbstractScreen screen, Workspace workspace) {
		super(pane);
		this.screen = screen;
		this.workspace = workspace;
		makePane();
	}

	@Override
	protected void makePane() {
		box = new VBox();
		box.minWidthProperty().bind(screen.getScene().widthProperty());
		mainMenu = new MenuBar();
		createMenuBar(mainMenu);
		toolBar = new ToolBar();
		makeTools(toolBar);
		box.getChildren().add(mainMenu);
		box.getChildren().add(toolBar);
		pane.add(box, 0, 0);
	}

	private void makeTools(ToolBar toolBar) {
		Button backButton = makeButton("back", e -> screen.setNextScreen(new StartScreen()));
		Button addButton = makeButton("add", e -> workspace.addLevel());
		toolBar.getItems().addAll(backButton, addButton);
	}

	private void createMenuBar(MenuBar mainMenu) {
		MenuItem load = makeMenuItem("Load Game", null);
		MenuItem save = makeMenuItem("Save Game", null);
		Menu file = addToMenu(new Menu("File"), load, save);

		MenuItem addLevel = makeMenuItem("Add New Level", e -> workspace.addLevel());
		Menu edit = addToMenu(new Menu("Edit"), addLevel);

		CheckMenuItem toolbar = new CheckMenuItem("Toolbar");
		toolbar.selectedProperty().setValue(true);
		toolbar.selectedProperty().addListener(e -> toggleToolbar(toolbar.selectedProperty().getValue()));
		Menu hideAndShow = addToMenu(new Menu("Hide/Show"), toolbar);
		makeComponentCheckMenus(hideAndShow);
		CheckMenuItem fullscreen = new CheckMenuItem("Full Screen");
		fullscreen.selectedProperty().bindBidirectional(screen.getFullscreenProperty());
		Menu window = addToMenu(new Menu("Window"), fullscreen, hideAndShow);
		makeMenuBar(mainMenu, file, edit, window);
	}

	private void toggleToolbar(Boolean value) {
		if (value) {
			box.getChildren().add(toolBar);
		} else {
			box.getChildren().remove(toolBar);
		}
	}

	private void makeComponentCheckMenus(Menu window) {
		for (AbstractDockElement c : screen.getComponents()) {
			CheckMenuItem item = new CheckMenuItem(myResources.getString(c.getClass().getSimpleName()));
			item.selectedProperty().bindBidirectional(c.isShowing());
			addToMenu(window, item);
		}
	}
}