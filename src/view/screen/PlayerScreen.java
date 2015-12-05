package view.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import exceptions.EngineException;
import exceptions.data.GameFileException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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

public class PlayerScreen extends AbstractScreen {

	private ControlBarPlayer controlBarPlayer;
	private PlayerController playerController;
	private ArrayList<GridPane> dockPanes;
	private ArrayList<GridPane> homePanes;
	private ActorMonitor monitor;
	//	private Workspace w;

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
		try{
			playerController.resume();
		} catch (GameFileException e){
			showWarning("Resume Game Error", "No game has been loaded yet!");
		}
	}

	/**
	 * Causes the PlayerControllers game loop to be paused. 
	 */
	public void pause() {
		try{
			playerController.pause();
		} catch (GameFileException e){
			showWarning("Pause Game Error", "No game has been loaded yet!");
		}
	}

	@Override
	protected void makeScene() {
		BorderPane r = new BorderPane();
		makePanes(2);
		controlBarPlayer = new ControlBarPlayer(myPanes.get(0), this, WIDTH);
		r.setTop(myPanes.get(0));
		//		w = new Workspace(myPanes.get(1), this);
		r.setCenter(myPanes.get(1));
		root = r;
		r.setTop(myPanes.get(0));
		r.setCenter(myPanes.get(1));
		scene = new Scene(root, WIDTH, HEIGHT);
		this.playerController = new PlayerController(scene);

		dockPanes = new ArrayList<GridPane>();
		homePanes = new ArrayList<GridPane>();
		for (int i = 0; i < 3; i++) {
			dockPanes.add(new GridPane());
			homePanes.add(new GridPane());
		}
		GridPane rightPane = new GridPane();
		rightPane.add(homePanes.get(0), 0, 0);
		rightPane.add(homePanes.get(1), 0, 1);
		rightPane.setAlignment(Pos.CENTER);
		r.setRight(rightPane);
		GridPane bottomPane = new GridPane();
		bottomPane.add(homePanes.get(2), 0, 0);
		r.setBottom(bottomPane);
		components = new ArrayList<AbstractDockElement>(); //No components yet! 
		monitor = new ActorMonitor(dockPanes.get(0), homePanes.get(0),
				myResources.getString("monitorname"), this, playerController);
		playerController.addMonitor(monitor);
		components.add(monitor);
	}

	// TODO: David: need a stage eventually for the line: fileChooser.showOpenDialog(null);
	// You want to force the user to choose
	/**
	 * Method that allows for a game to be loaded. Brings up a file selector using 'FileChooser' class 
	 */
	public void loadGame() {
		System.out.println("Testing");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Game File Loader");
		fileChooser.setInitialDirectory(new File("."));
		System.out.println(playerController);
		File file = fileChooser.showOpenDialog(null);

		try {
			playerController.loadGame(file.getAbsolutePath());
		} catch (GameFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException ee) {
			//			ee.printStackTrace();
			System.err.println("Level exception!");
		}
		monitor.initializePane();
		monitor.refresh();
		configureObserverRelationships();
		controlBarPlayer.initializeComponents();
	}

	/**
	 * Method that calls for the player to save the game state. 
	 */
	public void saveState () {
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
	public void loadState () {
		//TODO: do gui stuff
		try {
			File loadFile = FileChooserUtility.load(scene.getWindow());
			playerController.loadState(loadFile.getPath());
		} catch (GameFileException e) {
			System.out.println("Unable to load game state");
		}
	}

	//TODO: Implement, check the controller to see
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	private void configureObserverRelationships() {
		playerController.getState().addObserver(monitor);
	}

	public void resetOrReplay(String type) {
		try {
		     switch (type) {
		         case "Replay Level":  playerController.replayLevel();;
		         	break;
		         case "Reset Game": playerController.resetGame();;
		         	break;
		     }
			//TODO: Needs to reset the map as well
		} catch (NullPointerException e) {
			showWarning("Game Reset Error", "No Game Laoded!");
		} catch (GameFileException e) {
			showWarning("Game Reset Error", "Unable to " + type);
			e.printStackTrace();
		}
	}
	
	public void confirmRestartOrReplay(String type){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(type);
		alert.setContentText("Are you sure you'd like to?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    alert.hide();
		    resetOrReplay(type);
		} else {
		    alert.hide();
		}
	}

}
