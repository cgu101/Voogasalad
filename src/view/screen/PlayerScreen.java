package view.screen;

import exceptions.engine.NullGameException;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
	
	// TODO: find a file
	public void loadGame() {
		
	}

	//TODO: Implement, check the controller to see
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
