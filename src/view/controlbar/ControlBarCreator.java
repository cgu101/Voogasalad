package view.controlbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Observer;

import authoring.model.level.Level;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import network.framework.GameWindow;
import network.framework.format.Mail;
import network.framework.format.Request;
import network.instances.DataDecorator;
import network.util.PostalNetwork;
import view.element.AbstractDockElement;
import view.element.ActorBrowser;
import view.screen.CreatorScreen;
import view.screen.StartScreen;

public class ControlBarCreator extends ControlBar implements Observer {

	private static final String DEFAULT_IP = "localhost";

	private CreatorScreen screen;
	private GameWindow gameWindow;

	/**
	 * TODO: David: It would be preferable to have these in the CreatorScreen
	 */
	private MenuBar mainMenu;
	private ToolBar toolBar;
	private VBox box;

	public ControlBarCreator () {
		this(new CreatorScreen());
	}

	public ControlBarCreator(CreatorScreen screen) {
		super(screen.getDefaultPane());
		this.gameWindow = new GameWindow(DEFAULT_IP);
		this.screen = screen;

		initializeObservers();
		makePane();
	}

	/**
	 * CreatorScren:
	 * 	 o--> network
	 * ControlBarCreator:
	 * 	 o--> Workspace(...?)
	 *    o--> LevelInterface(...)
	 *     o--> ActorView(...)
	 *     o--> InteractionCell(...)
	 *   .
	 *   .
	 *   . 
	 */
	private void initializeObservers() {
		gameWindow.addObserver(this.screen);
		this.screen.getWorkspace().addObserver(this);
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
		Button backButton = makeButton("back", e -> {screen.setNextScreen(new StartScreen()); System.out.println("BRIIIIINGIT");});
		Button addButton = makeButton("add", e -> addNewLevel());
		Button leftButton = makeButton("left", e -> screen.getWorkspace().moveLevel(true));
		Button rightButton = makeButton("right", e -> screen.getWorkspace().moveLevel(false));
		Button splashButton = makeButton("splash", e -> screen.getWorkspace().addSplashScreen());
		Button newActor = makeButton("new", e -> addActor());

		toolBar.getItems().addAll(backButton, new Separator(), leftButton, rightButton, addButton, splashButton,
				new Separator(), newActor);
	}

	private void createMenuBar(MenuBar mainMenu) {
		MenuItem load = makeMenuItem(myResources.getString("load"), e -> screen.loadGame());
		MenuItem save = makeMenuItem(myResources.getString("save"), e -> screen.saveGame());
		MenuItem exit = makeMenuItem(myResources.getString("exit"), e -> Platform.exit(), KeyCode.E,
				KeyCombination.CONTROL_DOWN);
		Menu file = addToMenu(new Menu(myResources.getString("file")), load, save, exit);

		MenuItem addLevel = makeMenuItem(myResources.getString("newLevel"), 
				e -> addNewLevel(), KeyCode.T, KeyCombination.CONTROL_DOWN);
		MenuItem addSplash = makeMenuItem(myResources.getString("newSplash"), e -> screen.getWorkspace().addSplashScreen(), KeyCode.R,
				KeyCombination.CONTROL_DOWN);
		MenuItem addActor = makeMenuItem(myResources.getString("newActor"), e -> findActorBrowser().addNewActor(),
				KeyCode.N, KeyCombination.CONTROL_DOWN);
		MenuItem changeBackground = makeMenuItem(myResources.getString("background"), e -> updateBackground());
		Menu edit = addToMenu(new Menu(myResources.getString("edit")), addLevel, addSplash, addActor, changeBackground);

		CheckMenuItem toolbar = new CheckMenuItem(myResources.getString("toolbar"));
		toolbar.selectedProperty().setValue(true);
		toolbar.selectedProperty().addListener(e -> toggleToolbar(toolbar.selectedProperty().getValue()));

		Menu hideAndShow = addToMenu(new Menu(myResources.getString("hideshow")), toolbar);
		makeComponentCheckMenus(hideAndShow);

		CheckMenuItem fullscreen = new CheckMenuItem(myResources.getString("fullscreen"));
		fullscreen.setAccelerator(new KeyCodeCombination(KeyCode.F6));
		fullscreen.selectedProperty().bindBidirectional(screen.getFullscreenProperty());

		CheckMenuItem doubleLists = new CheckMenuItem(myResources.getString("dualactors"));
		doubleLists.selectedProperty().bindBidirectional(findActorBrowser().getDoubleListsProperty());

		Menu window = addToMenu(new Menu(myResources.getString("window")), fullscreen, hideAndShow, doubleLists);
		makeMenuBar(mainMenu, file, edit, window);
	}
	
	private void addNewLevel () {
		Level newLevel = new Level(Integer.toString(screen.getGame().getLevels().size()+1));
		DataDecorator dataMail = new DataDecorator(Request.ADD, newLevel, new ArrayDeque<String>());
		screen.getWorkspace().forward(dataMail.getPath(), dataMail);
		screen.getWorkspace().updateObservers(dataMail);
	}

	private void addActor() {
		findActorBrowser().addNewActor();
		if (!findActorBrowser().getShowingProperty().getValue()) {
			findActorBrowser().getShowingProperty().setValue(true);
		}
	}

	private void updateBackground() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(myResources.getString("background"));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));

		File file = fileChooser.showOpenDialog(null);

		try {
			Image backgroundImage = new Image(file.toURI().toURL().toExternalForm(), 60, 0, true, false);
			screen.getWorkspace().getCurrentLevel().updateBackground(backgroundImage);
		} catch (IOException ex) {
			// Alert fail = new Alert(AlertType.ERROR, "Unable to Load Image",
			// ButtonType.OK);
			// fail.showAndWait();
		}

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
			item.selectedProperty().bindBidirectional(c.getShowingProperty());
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

	public GameWindow getGameWindow() {
		return gameWindow;
	}

	public Scene getVisual() {
		return screen.getScene();
	}

	public CreatorScreen getScreen () {
		return screen;
	}

	@Override
	public void update(Observable o, Object arg) {
		screen.setGame(screen.getWorkspace().getGame()); //TODO perhaps unneeded

		if (arg instanceof Observable) {
			((Observable) arg).addObserver(this);
		} else if (arg instanceof Mail) {
			PostalNetwork.packageAndDeliver(this.gameWindow, (Mail) arg);
		}

	}
}