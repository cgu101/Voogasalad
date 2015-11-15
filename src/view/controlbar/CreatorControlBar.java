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
import view.element.ActorBrowser;
import view.element.Workspace;
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
		Button leftButton = makeButton("left", e -> workspace.moveLevelLeft());
		Button rightButton = makeButton("right", e -> workspace.moveLevelRight());
		toolBar.getItems().addAll(backButton, leftButton, rightButton, addButton);
	}

	private void createMenuBar(MenuBar mainMenu) {
		MenuItem load = makeMenuItem(myResources.getString("load"), null);
		MenuItem save = makeMenuItem(myResources.getString("save"), null);
		Menu file = addToMenu(new Menu(myResources.getString("file")), load, save);

		MenuItem addLevel = makeMenuItem(myResources.getString("newLevel"), e -> workspace.addLevel());
		MenuItem addActor = makeMenuItem(myResources.getString("newActor"), e -> findActorBrowser().addNewActor());
		Menu edit = addToMenu(new Menu(myResources.getString("edit")), addLevel, addActor);

		CheckMenuItem toolbar = new CheckMenuItem(myResources.getString("toolbar"));
		toolbar.selectedProperty().setValue(true);
		toolbar.selectedProperty().addListener(e -> toggleToolbar(toolbar.selectedProperty().getValue()));
		Menu hideAndShow = addToMenu(new Menu(myResources.getString("hideshow")), toolbar);
		makeComponentCheckMenus(hideAndShow);
		CheckMenuItem fullscreen = new CheckMenuItem(myResources.getString("fullscreen"));
		fullscreen.selectedProperty().bindBidirectional(screen.getFullscreenProperty());

		CheckMenuItem doubleLists = new CheckMenuItem(myResources.getString("dualactors"));
		doubleLists.selectedProperty().bindBidirectional(findActorBrowser().getDoubleListsProperty());
		Menu window = addToMenu(new Menu(myResources.getString("window")), fullscreen, hideAndShow, doubleLists);
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

	private ActorBrowser findActorBrowser() {
		for (AbstractDockElement c : screen.getComponents()) {
			if (c instanceof ActorBrowser) {
				return (ActorBrowser) c;
			}
		}
		return null;
	}
}