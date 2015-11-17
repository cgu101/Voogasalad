package view.screen;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import view.controlbar.ControlBarCreator;
import view.element.AbstractDockElement;
import view.element.ActorBrowser;
import view.element.ActorEditor;
import view.element.Workspace;

public class CreatorScreen extends AbstractScreen {

	private ControlBarCreator t;
	private Workspace w;
	private ArrayList<GridPane> dockPanes;
	private ArrayList<GridPane> homePanes;

	public CreatorScreen() {
		findResources();
		WIDTH = Integer.parseInt(myResources.getString("width"));
		HEIGHT = Integer.parseInt(myResources.getString("height"));

		makeScene();
		root.prefHeightProperty().bind(scene.heightProperty());
		root.prefWidthProperty().bind(scene.widthProperty());
		this.title = myResources.getString("title");
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
		rightPane.add(homePanes.get(1), 0, 1);
		rightPane.setAlignment(Pos.CENTER);
		r.setRight(rightPane);
		GridPane bottomPane = new GridPane();
		bottomPane.add(homePanes.get(2), 0, 0);
		r.setBottom(bottomPane);
		components = new ArrayList<AbstractDockElement>();
		ActorBrowser browser = new ActorBrowser(dockPanes.get(0), homePanes.get(0),
				myResources.getString("browsername"), this);
		components.add(browser);
		ActorEditor editor = new ActorEditor(dockPanes.get(1), homePanes.get(1), myResources.getString("editorname"),
				this, browser);
		components.add(editor);
		t = new ControlBarCreator(myPanes.get(0), this, w);
	}
	//TODO
	public void saveGame(){
		
	}
	//TODO
	public void loadGame(){
		
	}
}
