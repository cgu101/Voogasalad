package view.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import exceptions.EngineException;
import exceptions.data.GameFileException;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import player.controller.PlayerController;
import util.FileChooserUtility;
import view.controlbar.ControlBarPlayer;
import view.element.AbstractDockElement;
import view.element.ActorMonitor;
import view.element.GameInfoMonitor;
import view.map.PlayerMapSliders;

public class PlayerScreen extends AbstractScreen {

	private ControlBarPlayer controlBarPlayer;
	private PlayerController playerController;
	private ArrayList<GridPane> homePanes;
	private ActorMonitor actorMonitor;
	private GameInfoMonitor gameInfoMonitor;
	private view.map.Map map;
	PlayerMapSliders mapSlider;
	// private Workspace w;

	public PlayerScreen() {
		findResources();
		WIDTH = Integer.parseInt(myResources.getString("width"));
		HEIGHT = Integer.parseInt(myResources.getString("height"));
		this.title = myResources.getString("title");

		makeScene();
	}

	/**
	 * Causes the PlayerControllers game loop to be resumed.
	 */
	public void resume() {
		try {
			playerController.resume();
		} catch (GameFileException e) {
			showWarning("Resume Game Error", "No game has been loaded yet!");
		}
	}

	/**
	 * Causes the PlayerControllers game loop to be paused.
	 */
	public void pause() {
		try {
			playerController.pause();
		} catch (GameFileException e) {
			showWarning("Pause Game Error", "No game has been loaded yet!");
		}
	}

	@Override
	protected void makeScene() {
		BorderPane r = new BorderPane();
		root = r;
		scene = new Scene(root, WIDTH, HEIGHT);
		makePanes(2);
		r.setTop(myPanes.get(0));
		homePanes = new ArrayList<GridPane>();
		this.playerController = new PlayerController(scene, myPanes.get(1));
		map = playerController.getMap();
		GridPane mapPane = new GridPane();
		mapPane.add(myPanes.get(1), 0, 0);
		for (int i = 0; i < 3; i++) {
			homePanes.add(new GridPane());
		}
		GridPane rightPane = new GridPane();
		rightPane.add(homePanes.get(0), 0, 0);
		rightPane.add(homePanes.get(1), 0, 1);
		r.setRight(rightPane);

		components = new ArrayList<AbstractDockElement>();
		gameInfoMonitor = new GameInfoMonitor(homePanes.get(0), myResources.getString("gameinfoname"), this,
				playerController);

		actorMonitor = new ActorMonitor(homePanes.get(1), myResources.getString("monitorname"), this, playerController);
		mapSlider = new PlayerMapSliders(homePanes.get(2), myResources.getString("slidername"), this);
		mapPane.add(homePanes.get(2), 0, 1);
		r.setCenter(mapPane);
		mapSlider.initializeMap(map);
		components.add(mapSlider);
		components.add(actorMonitor);
		components.add(gameInfoMonitor);
		playerController.addMonitor(actorMonitor);
		controlBarPlayer = new ControlBarPlayer(myPanes.get(0), this, WIDTH);
		configureMap(gameInfoMonitor, actorMonitor);
		map.setPanEnabled(false);
	}

	/**
	 * Method that allows for a game to be loaded. Brings up a file selector
	 * using 'FileChooser' class
	 */
	public void loadGame() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Game File Loader");
		fileChooser.setInitialDirectory(new File("."));
		File file = fileChooser.showOpenDialog(null);

		if (file == null) {
			return;
		}

		try {
			playerController.loadGame(file.getAbsolutePath());
		} catch (GameFileException e) {
			e.printStackTrace();
		} catch (EngineException ee) {
			// ee.printStackTrace();
			// TODO: handle these exceptions
			System.err.println("Level exception!");
		}
		gameInfoMonitor.initializePane();
		actorMonitor.initializePane();
		actorMonitor.refresh();
		configureObserverRelationships();
		controlBarPlayer.initializeComponents();
		for (AbstractDockElement c : getComponents()) {
			c.getShowingProperty().setValue(true);
		}
	}

	/**
	 * Method that calls for the player to save the game state.
	 */
	public void saveState() {
		System.out.println("Testing saving game state ");

		try {
			File saveFile = FileChooserUtility.save(scene.getWindow());
			playerController.saveState(saveFile.getAbsolutePath());
		} catch (GameFileException e) {
			System.out.println("Unable to save game state");
		}
	}

	/**
	 * Method that calls for the player to load a game state.
	 */
	public void loadState() {
		try {
			File loadFile = FileChooserUtility.load(scene.getWindow());
			playerController.loadState(loadFile.getPath());
		} catch (GameFileException e) {
			System.out.println("Unable to load game state");
		}
	}

	// TODO: Implement, check the controller to see
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	private void configureObserverRelationships() {
		playerController.getState().addObserver(actorMonitor);
	}

	public void resetOrReplay(String type) {
		try {
			switch (type) {
			case "Replay Level":
				playerController.replayLevel();
				;
				break;
			case "Reset Game":
				playerController.resetGame();
				;
				break;
			}
			// TODO: Needs to reset the map as well
		} catch (NullPointerException e) {
			showWarning("Game Reset Error", "No Game Laoded!");
		} catch (GameFileException e) {
			showWarning("Game Reset Error", "Unable to " + type);
			e.printStackTrace();
		}
	}

	public void confirmRestartOrReplay(String type) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(type);
		alert.setContentText("Are you sure you'd like to?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			alert.hide();
			resetOrReplay(type);
		} else {
			alert.hide();
		}
	}

	private void configureMap(GameInfoMonitor info, ActorMonitor monitor) {
		fullscreen.addListener(e -> manageMapSize(fullscreen.getValue(), info.getDockedProperty().getValue(),
				monitor.getDockedProperty().getValue()));
		info.getDockedProperty().addListener(e -> manageMapSize(fullscreen.getValue(),
				info.getDockedProperty().getValue(), monitor.getDockedProperty().getValue()));
		monitor.getDockedProperty().addListener(e -> manageMapSize(fullscreen.getValue(),
				info.getDockedProperty().getValue(), monitor.getDockedProperty().getValue()));
	}

	private void manageMapSize(boolean fullscreen, boolean monitor, boolean editor) {
		if (!fullscreen) {
			if (!monitor && !editor) {
				map.setMapDimensions(Double.parseDouble(myResources.getString("mediumsmallwidth")), Double.parseDouble(myResources.getString("smallscreenheight")));
			} else {
				map.setMapDimensions(Double.parseDouble(myResources.getString("smallestwidth")), Double.parseDouble(myResources.getString("smallscreenheight")));
			}
		} else {
			if (!monitor && !editor) {
				map.setMapDimensions(Double.parseDouble(myResources.getString("suffrage")), Double.parseDouble(myResources.getString("fullscreenheight")));
			} else {
				map.setMapDimensions(Double.parseDouble(myResources.getString("mediumbigwidth")), Double.parseDouble(myResources.getString("fullscreenheight")));
			}
		}
	}
}
