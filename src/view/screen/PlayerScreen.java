package view.screen;

import java.io.File;

import exceptions.engine.NullGameException;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import player.PlayerController;
import view.controlbar.ControlBarPlayer;

public class PlayerScreen extends AbstractScreen {

	private ControlBarPlayer t;
	private PlayerController playerController;

	public PlayerScreen() {
		findResources();
		WIDTH = Integer.parseInt(myResources.getString("width"));
		HEIGHT = Integer.parseInt(myResources.getString("height"));
		this.title = myResources.getString("title");
		this.playerController = new PlayerController();
		
		makeScene();
		scene = new Scene(root, WIDTH, HEIGHT);
	}

	//TODO: Throw NullGameException when Game hasn't been loaded yet
	public void resume() {
		playerController.resume();
	}

	//TODO: Throw NullGameException when Game hasn't been loaded yet
	public void pause() {
		playerController.pause();
	}
	@Override
	protected void makeScene() {
		BorderPane r = new BorderPane();
		makePanes(2);
		t = new ControlBarPlayer(myPanes.get(0), this, WIDTH);
		r.setTop(myPanes.get(0));

		root = r;
	}
	
	// TODO: David: need a stage eventually for the line: fileChooser.showOpenDialog(null);
	// You want to force the user to choose
	public void loadGame() {
		System.out.println("Testing");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Game File Loader");
		fileChooser.setInitialDirectory(new File("."));
		System.out.println(playerController);
		File file = fileChooser.showOpenDialog(null);
		
		playerController.loadGame(file);
	}

	//TODO: Implement, check the controller to see
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
