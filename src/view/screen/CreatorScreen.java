package view.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import authoring.controller.AuthoringController;
import authoring.controller.constructor.LevelConstructor;
import authoring.model.game.Game;
import data.XMLManager;
import exceptions.data.GameFileException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import util.FileChooserUtility;
import view.controlbar.ControlBarCreator;
import view.element.AbstractDockElement;
import view.element.ActorBrowser;
import view.element.ActorEditor;
import view.level.Workspace;

/**
 * Screen for authoring environment
 * 
 * @author David
 *
 */
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
				myResources.getString("browsername"), this, w);
		components.add(browser);
		ActorEditor editor = new ActorEditor(dockPanes.get(1), homePanes.get(1), myResources.getString("editorname"),
				this, browser, w);
		components.add(editor);
		t = new ControlBarCreator(myPanes.get(0), this, w);
	}

	// TODO
	public void saveGame() {
		System.out.println("Testing saving game ");

		List<LevelConstructor> levelConstructors = w.getLevels();

		try {
			Game game = AuthoringController.getGameWithLevels(levelConstructors);
			File saveFile = FileChooserUtility.save(scene.getWindow());

			String fileLocation = saveFile.getAbsolutePath();

			XMLManager.saveGame(game, fileLocation);
		} catch (GameFileException e) {
			System.out.println("Unable to save game");
		}
	}

	// TODO
	public void loadGame() {
		System.out.println("Testing loading game ");
	}
}
