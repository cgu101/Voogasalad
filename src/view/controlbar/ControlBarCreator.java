package view.controlbar;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Observable;
import java.util.Observer;

import authoring.files.properties.ActorProperties;
import authoring.model.game.Game;
import authoring.model.level.Level;
import authoring.model.properties.Property;
import authoring.model.tree.InteractionTreeNode;
import data.XMLManager;
import exceptions.data.GameFileException;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import network.framework.GameWindow;
import network.framework.format.Mail;
import network.framework.format.Request;
import network.instances.DataDecorator;
import network.util.PostalNetwork;
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;
import view.element.AbstractDockElement;
import view.element.ActorBrowser;
import view.level.LevelType;
import view.screen.CreatorScreen;
import view.screen.StartScreen;

public class ControlBarCreator extends ControlBar implements Observer {

	private static final String DEFAULT_IP = "localhost";
//	private static final String LEVEL_ID = "Level ";
//	private static final String SPLASH_ID = "Splash ";

	private CreatorScreen screen;
	private GameWindow gameWindow;

	/**
	 * TODO: David: It would be preferable to have these in the CreatorScreen
	 */
	private MenuBar mainMenu;
	private ToolBar toolBar;
	private VBox box;

	public ControlBarCreator() {
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
	 * CreatorScren: o--> network ControlBarCreator: o--> Workspace(...?) o-->
	 * LevelInterface(...) o--> ActorView(...) o--> InteractionCell(...) . . .
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
		Button backButton = makeButton("back", e -> {
			for (AbstractDockElement c : screen.getComponents()) {
				c.getShowingProperty().setValue(false);
			}
			screen.setNextScreen(new StartScreen());
		});
		setHoverAndExitAnimations(backButton);

		Button addButton = makeButton("add", e -> addNewLevel());
		setHoverAndExitAnimations(addButton);

		Button leftButton = makeButton("left", e -> screen.getWorkspace().moveLevel(true));
		setHoverAndExitAnimations(leftButton);

		Button rightButton = makeButton("right", e -> screen.getWorkspace().moveLevel(false));
		setHoverAndExitAnimations(rightButton);

		Button splashButton = makeButton("splash", e -> addNewSplash());
		setHoverAndExitAnimations(splashButton);

		Button backgroundButton = makeButton("background", e -> updateBackground());
		setHoverAndExitAnimations(backgroundButton);

		Button newActor = makeButton("new", e -> addActor());
		setHoverAndExitAnimations(newActor);

		toolBar.getItems().addAll(backButton, new Separator(), addButton, splashButton, new Separator(), leftButton,
				rightButton, new Separator(), newActor, new Separator(), backgroundButton);
	}

	private void createMenuBar(MenuBar mainMenu) {
		//		MenuItem load = makeMenuItem(myResources.getString("load"), e -> screen.loadGame());
		MenuItem load = makeMenuItem(myResources.getString("load"), e -> this.loadGame());
		MenuItem save = makeMenuItem(myResources.getString("save"), e -> screen.saveGame());
		MenuItem exit = makeMenuItem(myResources.getString("exit"), e -> Platform.exit(), KeyCode.E,
				KeyCombination.CONTROL_DOWN);
		Menu file = addToMenu(new Menu(myResources.getString("file")), load, save, new SeparatorMenuItem(), exit);

		MenuItem addLevel = makeMenuItem(myResources.getString("newLevel"), e -> addNewLevel(), KeyCode.T,
				KeyCombination.CONTROL_DOWN);
		MenuItem addSplash = makeMenuItem(myResources.getString("newSplash"), e -> addNewSplash(), KeyCode.R,
				KeyCombination.CONTROL_DOWN);

		MenuItem addActor = makeMenuItem(myResources.getString("newActor"), e -> addActor(),
				KeyCode.N, KeyCombination.CONTROL_DOWN);
		MenuItem changeBackground = makeMenuItem(myResources.getString("background.message"), e -> updateBackground());
		Menu edit = addToMenu(new Menu(myResources.getString("edit")), addActor, new SeparatorMenuItem(), addLevel,
				addSplash, new SeparatorMenuItem(), changeBackground);

		CheckMenuItem toolbar = new CheckMenuItem(myResources.getString("toolbar"));
		toolbar.selectedProperty().setValue(true);
		toolbar.selectedProperty().addListener(e -> toggleToolbar(toolbar.selectedProperty().getValue()));

		Menu hideAndShow = addToMenu(new Menu(myResources.getString("hideshow")), toolbar);
		makeComponentCheckMenus(hideAndShow, screen);

		CheckMenuItem fullscreen = new CheckMenuItem(myResources.getString("fullscreen"));
		fullscreen.setAccelerator(new KeyCodeCombination(KeyCode.F6));
		fullscreen.selectedProperty().bindBidirectional(screen.getFullscreenProperty());

		CheckMenuItem doubleLists = new CheckMenuItem(myResources.getString("dualactors"));
		doubleLists.selectedProperty().bindBidirectional(findActorBrowser().getDoubleListsProperty());

		Menu window = addToMenu(new Menu(myResources.getString("window")), hideAndShow, doubleLists,
				new SeparatorMenuItem(), fullscreen);
		makeMenuBar(mainMenu, file, edit, window);
	}

	private void loadGame () {
		System.out.println("Testing loading game ");

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Game File Loader");
		fileChooser.setInitialDirectory(new File("."));
		File file = fileChooser.showOpenDialog(null);

		if (file == null) {
			return;
		}
		
		
		String fileName = file.getAbsolutePath();
		try {
			Game game = XMLManager.loadGame(fileName);
			
//			for (AbstractDockElement c : screen.getComponents()) {
//				c.getShowingProperty().setValue(false);
//			}
			
//			screen.setNextScreen(new CreatorScreen(game));
			
			Deque<String> a = new ArrayDeque<String>();
			
			for (Level l : game.getBundleLevels()) {
				Mail mail = new DataDecorator (Request.LOAD, l, a);
				screen.getWorkspace().forward(mail.getPath(), mail);
			}
			
			
//			CreatorScreen screen = new CreatorScreen(game);
			
//			this.pane = screen.getDefaultPane();
//			this.gameWindow = new GameWindow(DEFAULT_IP);
//			this.screen = screen;

//			initializeObservers();
//			makePane();
//			System.out.println("Load Succcess");
		} catch (GameFileException e) {
			e.printStackTrace();
			System.out.println("Something wrong with the game load");
		}
	}

	private void addNewLevel() {
		Level newLevel = new Level(Integer.toString(screen.getGame().getLevels().size()));
		newLevel.getPropertyBundle().add(new Property<String>(myResources.getString("type"), myResources.getString("leveltype")) );
		DataDecorator dataMail = new DataDecorator(Request.ADD, newLevel, screen.getWorkspace().getAnscestralPath());
		screen.forward(dataMail.getPath(), dataMail);

		screen.getWorkspace().updateObservers(dataMail);
		if (screen.getGame().getLevels().size() == 1) {
			toggleComponents(true, screen);
		}
	}

	private void addNewSplash() {
		Level newSplash = new Level(Integer.toString(screen.getGame().getLevels().size()));
		newSplash.getPropertyBundle().add(new Property<String>(myResources.getString("type"), myResources.getString("splashtype")) );
		DataDecorator dataMail = new DataDecorator(Request.TRANSITION, newSplash, screen.getWorkspace().getAnscestralPath());
		screen.forward(dataMail.getPath(), dataMail);
	}

	private void handleHover(Button b) {
		FadeTransition fadeTransition = 
				new FadeTransition(Duration.millis(300), b);
		fadeTransition.setFromValue(0.5);
		fadeTransition.setToValue(1.0);
		fadeTransition.setCycleCount(1);
		fadeTransition.setAutoReverse(true);
		fadeTransition.play();

		ScaleTransition scaleTransition = 
				new ScaleTransition(Duration.millis(300), b);
		scaleTransition.setToX(1.1);
		scaleTransition.setToY(1.1);
		scaleTransition.setCycleCount(1);
		scaleTransition.setAutoReverse(true);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(
				fadeTransition,
				scaleTransition
				);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
	}

	private void handleExit(Button b) {
		FadeTransition fadeTransition = 
				new FadeTransition(Duration.millis(300), b);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.5);
		fadeTransition.setCycleCount(1);
		fadeTransition.setAutoReverse(true);

		ScaleTransition scaleTransition = 
				new ScaleTransition(Duration.millis(300), b);
		scaleTransition.setToX(1);
		scaleTransition.setToY(1);
		scaleTransition.setCycleCount(1);
		scaleTransition.setAutoReverse(true);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(
				fadeTransition,
				scaleTransition
				);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
	}

	private void setHoverAndExitAnimations(Button b) {
		b.setOpacity(0.5);
		b.setOnMouseEntered(e -> handleHover(b));
		b.setOnMouseExited(e -> handleExit(b));
	}

	private void addActor() {
		findActorBrowser().createCustomGroup();
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
			String fileString = file.toURI().toString();
			Image backgroundImage = new Image(fileString);

			this.screen.getWorkspace().getCurrentLevel().updateBackground(backgroundImage);
			this.screen.getWorkspace().getCurrentLevel().updateLevelProperty(
					new Property<String>(PropertyKeyResource.getKey(PropertyKey.LEVEL_BACKGROUND_KEY),fileString));
		} catch (Exception e) {
			
		}
	}

	private void toggleToolbar(Boolean value) {
		if (value) {
			box.getChildren().add(toolBar);
		} else {
			box.getChildren().remove(toolBar);
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

	public CreatorScreen getScreen() {
		return screen;
	}

	@Override
	public void update(Observable o, Object arg) {
		screen.setGame(screen.getWorkspace().getGame()); // TODO perhaps

		if (arg instanceof Observable) {
			((Observable) arg).addObserver(this);
		} else if (arg instanceof Mail) {
			PostalNetwork.packageAndDeliver(this.gameWindow, (Mail) arg);
		} else if (arg instanceof InteractionTreeNode) {
			//this.screen.
		}

	}
}