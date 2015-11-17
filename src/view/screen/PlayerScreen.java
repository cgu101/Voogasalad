package view.screen;

import java.io.File;

import exceptions.data.GameFileException;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import player.PlayerController;
import view.controlbar.ControlBarPlayer;
import view.element.Workspace;

public class PlayerScreen extends AbstractScreen {

	private ControlBarPlayer t;
	private PlayerController playerController;
	private Workspace w;

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
		w = new Workspace(myPanes.get(1), this);
		r.setCenter(myPanes.get(1));
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

	public void saveState () {
		//TODO: do gui stuff
		String fileName = "";
		try {
			playerController.saveState(fileName);
		} catch (GameFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadState () {
		//TODO: do gui stuff
		String fileName = "";
		try {
			playerController.loadState(fileName);
		} catch (GameFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//TODO: Implement, check the controller to see
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
