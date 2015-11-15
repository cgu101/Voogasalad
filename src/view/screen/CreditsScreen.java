package view.screen;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import view.element.ControlBarGeneral;

/**
 * This class sets up the Settings Screen 
 */
public class CreditsScreen extends AbstractScreen {
	private ControlBarGeneral t;
	
	public CreditsScreen() {
		findResources();
		WIDTH = Integer.parseInt(myResources.getString("width"));
		HEIGHT = Integer.parseInt(myResources.getString("height"));
		this.title = myResources.getString("title");

		makeScene();
		scene = new Scene(root, WIDTH, HEIGHT);
	}
	
	@Override
	public void run() {
		
	}

	@Override
	protected void makeScene() {
		BorderPane r = new BorderPane();
		makePanes(2);
		t = new ControlBarGeneral(myPanes.get(0), this, WIDTH);
		r.setTop(myPanes.get(0));
		
		root = r;
	}

}
