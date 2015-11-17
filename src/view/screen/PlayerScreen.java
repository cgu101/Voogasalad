package view.screen;

import exceptions.data.GameFileException;
import exceptions.engine.NullGameException;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
		
		makeScene();
		scene = new Scene(root, WIDTH, HEIGHT);
		playerController = new PlayerController();
	}

	//TODO: Throw NullGameException when Game hasn't been loaded yet
	public void resume() {
		try{
			playerController.resume();
		} catch (GameFileException e){
			showWarning("Resume Game Error", "No game has been loaded yet!");
		}
	}

	//TODO: Throw NullGameException when Game hasn't been loaded yet
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
		t = new ControlBarPlayer(myPanes.get(0), this, WIDTH);
		r.setTop(myPanes.get(0));
		w = new Workspace(myPanes.get(1), this);
		r.setCenter(myPanes.get(1));
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
