package view.screen;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import view.element.ActorBrowser;
import view.element.CreatorControlBar;
import view.element.Workspace;

public class CreatorScreen extends AbstractScreen {

	private CreatorControlBar t;
	private Workspace w;
	private ArrayList<GridPane> dockPanes;
	private ArrayList<GridPane> homePanes;
	private ActorBrowser browser;
	private CheckMenuItem checkFullscreen;

	public CreatorScreen() {
		findResources();
		WIDTH = Integer.parseInt(myResources.getString("width"));
		HEIGHT = Integer.parseInt(myResources.getString("height"));
		checkFullscreen = new CheckMenuItem("Full Screen");
		checkFullscreen.setSelected(true);
		checkFullscreen.selectedProperty().addListener(e -> maxOrMin(checkFullscreen.isSelected()));
		
		makeScene();
		root.prefHeightProperty().bind(scene.heightProperty());
		root.prefWidthProperty().bind(scene.widthProperty());
		this.title = myResources.getString("title");
	}

	//TODO: this should work once the observer/observable relationship between stage and scene is fully set up.
	private void maxOrMin(boolean selected) {
		fullscreen = selected;
	}

	@Override
	public void run() {

	}

	@Override
	protected void makeScene() {
		BorderPane r = new BorderPane();
		root = r;
		scene = new Scene(root, WIDTH, HEIGHT);
		makePanes(2);
		w = new Workspace(myPanes.get(1), this);
		t = new CreatorControlBar(myPanes.get(0), this, w);
		r.setTop(myPanes.get(0));
		r.setCenter(myPanes.get(1));
		dockPanes = new ArrayList<GridPane>();
		homePanes = new ArrayList<GridPane>();
		for (int i = 0; i < 3; i++) {
			dockPanes.add(new GridPane());
			homePanes.add(new GridPane());
		}
		GridPane rightPane = new GridPane();
		rightPane.add(homePanes.get(0), 0, 0);
		r.setRight(rightPane);
		GridPane bottomPane = new GridPane();
		bottomPane.add(homePanes.get(1), 0, 0);
		bottomPane.add(homePanes.get(2), 1, 0);
		r.setBottom(bottomPane);
		browser = new ActorBrowser(dockPanes.get(0), homePanes.get(0), myResources.getString("browsername"), this);
		
		// this line seems like poor design but let's go with it for now
		t.addToToolbar(checkFullscreen);
		t.addToToolbar(browser.getMenuItem());
	}
}
