package view.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import authoring.controller.AuthoringController;
import authoring.controller.constructor.levelwriter.LevelConstructor;
import authoring.model.game.Game;
import data.XMLManager;
import exceptions.data.GameFileException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import network.test.GameWindow;
import util.FileChooserUtility;
import view.element.AbstractDockElement;
import view.element.ActorBrowser;
import view.element.ActorEditor;
import view.handler.ActorHandlerToolbar;
import view.level.Workspace;
import view.map.MapSliders;

/**
 * Screen for authoring environment
 * 
 * @author David
 *
 */
public class CreatorScreen extends AbstractScreen implements Observer {
	private static final int DEFAULT_MAP_PANE_INDEX = 0;

	private Workspace w;
	private ArrayList<GridPane> dockPanes;
	private ArrayList<GridPane> homePanes;
	
	private Game game;

	public CreatorScreen() {
		this(null);
	}
	
	public CreatorScreen (Game game) {
		this.game = game;
		findResources();
		WIDTH = Integer.parseInt(myResources.getString("width"));
		HEIGHT = Integer.parseInt(myResources.getString("height"));
		makeScene();
		root.prefHeightProperty().bind(scene.heightProperty());
		root.prefWidthProperty().bind(scene.widthProperty());
		this.title = myResources.getString("title");
	}
	
	public CreatorScreen (Game game, GameWindow gw) {
		this(game);
		w.addNetwork(gw);
	}

	@Override
	public void run() {
		//System.out.println("test run");
	}

	@Override
	protected void makeScene() {
		BorderPane r = new BorderPane();
		root = r;
		scene = new Scene(root, WIDTH, HEIGHT);
		makePanes(2);
		w = new Workspace(myPanes.get(1), this);
		r.setTop(myPanes.get(0));
		GridPane mapPane = new GridPane();
		mapPane.add(myPanes.get(1), 0, 1);
		r.setCenter(mapPane);
		dockPanes = new ArrayList<GridPane>();
		homePanes = new ArrayList<GridPane>();
		for (int i = 0; i < 4; i++) {
			dockPanes.add(new GridPane());
			homePanes.add(new GridPane());
		}
		mapPane.add(homePanes.get(3), 0, 0);
		mapPane.add(homePanes.get(2), 0, 2);
		GridPane rightPane = new GridPane();
		rightPane.add(homePanes.get(0), 0, 0);
		rightPane.add(homePanes.get(1), 0, 1);
		rightPane.setAlignment(Pos.CENTER);
		r.setRight(rightPane);
		components = new ArrayList<AbstractDockElement>();
		ActorBrowser browser = new ActorBrowser(dockPanes.get(0), homePanes.get(0),
				myResources.getString("browsername"), this, w);
		components.add(browser);
		ActorEditor editor = new ActorEditor(dockPanes.get(1), homePanes.get(1), myResources.getString("editorname"),
				this, browser, w);
		components.add(editor);
		MapSliders slider = new MapSliders(dockPanes.get(2), homePanes.get(2), myResources.getString("slidername"),
				this, w);
		components.add(slider);
		ActorHandlerToolbar aet = new ActorHandlerToolbar(dockPanes.get(3), homePanes.get(3), myResources.getString("toolbarname"), this, w);
		components.add(aet);
		
//		t = new ControlBarCreator(myPanes.get(0), this);
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
	
	public Workspace getWorkspace () {
		return w;
	}
	
	public GridPane getDefaultPane () {
		return myPanes.get(DEFAULT_MAP_PANE_INDEX);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("OBserver update");
		
		Game receivedGame = (Game) arg;
		w.updateVisual((GameWindow) o, (Game) arg);
	}
}
