package view.screen;

import java.io.File;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Observable;
import java.util.Observer;

import authoring.model.Anscestral;
import authoring.model.bundles.Bundle;
import authoring.model.game.Game;
import authoring.model.properties.Property;
import data.XMLManager;
import exceptions.EngineException;
import exceptions.data.GameFileException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import network.framework.GameWindow;
import network.framework.format.Mail;
import resources.keys.PropertyKey;
import resources.keys.PropertyKeyResource;
import util.FileChooserUtility;
import view.element.AbstractDockElement;
import view.element.ActorBrowser;
import view.element.ActorEditor;
import view.handler.ActorHandlerToolbar;
import view.level.Workspace;
import view.map.CreatorMapSliders;

/**
 * Screen for authoring environment
 * 
 * @author David
 *
 */
public class CreatorScreen extends AbstractScreen implements Observer, Anscestral {
	private static final int DEFAULT_MAP_PANE_INDEX = 0;

	private Workspace w;
	private ArrayList<GridPane> homePanes;

	private Game game;

	public CreatorScreen() {
		this(new Game());
	}

	public CreatorScreen(Game game) {
		refresh(game);
	}
	
	public void refresh(Game game) {
		this.game = game;
		findResources();
		WIDTH = Integer.parseInt(myResources.getString("width"));
		HEIGHT = Integer.parseInt(myResources.getString("height"));
		makeScene();
		root.prefHeightProperty().bind(scene.heightProperty());
		root.prefWidthProperty().bind(scene.widthProperty());
		this.title = myResources.getString("title");
	}

	public CreatorScreen(Game game, GameWindow gw) {
		this(game);
		// w.addNetwork(gw);
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
		w = new Workspace(myPanes.get(1), this, this.game);
		r.setTop(myPanes.get(0));
		GridPane mapPane = new GridPane();
		mapPane.add(myPanes.get(1), 0, 1);
		r.setCenter(mapPane);
		homePanes = new ArrayList<GridPane>();
		for (int i = 0; i < 4; i++) {
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
		ActorBrowser browser = new ActorBrowser(homePanes.get(0), myResources.getString("browsername"), this, w);
		components.add(browser);
		ActorEditor editor = new ActorEditor(homePanes.get(1), myResources.getString("editorname"), this, browser, w);
		components.add(editor);
		CreatorMapSliders slider = new CreatorMapSliders(homePanes.get(2), myResources.getString("slidername"), this,
				w);
		components.add(slider);
		ActorHandlerToolbar aet = new ActorHandlerToolbar(homePanes.get(3), myResources.getString("toolbarname"), this,
				w);
		components.add(aet);
		configureMap(browser, editor);
	}

	private void configureMap(ActorBrowser browser, ActorEditor editor) {
		fullscreen.addListener(e -> manageMapSize(fullscreen.getValue(), browser.getDockedProperty().getValue(),
				editor.getDockedProperty().getValue()));
		browser.getDockedProperty().addListener(e -> manageMapSize(fullscreen.getValue(),
				browser.getDockedProperty().getValue(), editor.getDockedProperty().getValue()));
		editor.getDockedProperty().addListener(e -> manageMapSize(fullscreen.getValue(),
				browser.getDockedProperty().getValue(), editor.getDockedProperty().getValue()));
	}

	private void manageMapSize(boolean fullscreen, boolean browser, boolean editor) {
		if (w.getCurrentLevel() == null) {
			return;
		} else if (!fullscreen) {
			if (!browser && !editor) {
				w.getCurrentLevel().setMapDimensions(Double.parseDouble(myResources.getString("med1width")), Double.parseDouble(myResources.getString("smallscreenheight")));
			} else {
				w.getCurrentLevel().setMapDimensions(Double.parseDouble(myResources.getString("smallestwidth")), Double.parseDouble(myResources.getString("smallscreenheight")));
			}
		} else {
			w.refresh();
			if (!browser && !editor) {
				w.getCurrentLevel().setMapDimensions(Double.parseDouble(myResources.getString("largestwidth")), Double.parseDouble(myResources.getString("bigscreenheight")));
			} else {
				w.getCurrentLevel().setMapDimensions(Double.parseDouble(myResources.getString("med2width")), Double.parseDouble(myResources.getString("bigscreenheight")));
			}
		}
	}
	private void setProperties (Game game) {
		Bundle<Property<?>> bundle = new Bundle<Property<?>>();
		bundle.add(new Property<String>(PropertyKeyResource.getKey(PropertyKey.GAME_ID_KEY), "name"));
		bundle.add(new Property<String>(PropertyKeyResource.getKey(PropertyKey.GAME_DESCRIPTION_KEY), "description"));
		bundle.add(new Property<String>(PropertyKeyResource.getKey(PropertyKey.INITIAL_LEVEL_KEY), "0"));
		bundle.add(new Property<String>(PropertyKeyResource.getKey(PropertyKey.GAME_LEVEL_COUNT_KEY), Integer.toString(game.getBundleLevels().getSize())));
		game.addAllProperties(bundle);
	}
	
	public void saveGame() {
		try {
			setProperties(game);
			
			File saveFile = FileChooserUtility.save(scene.getWindow());

			String fileLocation = saveFile.getAbsolutePath();

			XMLManager.saveGame(game, fileLocation);
		} catch (GameFileException e) {
			System.out.println("Unable to save game");
		}
	}

	// TODO
//	public void loadGame() {
//		System.out.println("Testing loading game ");
//		try {
//			File loadFile = FileChooserUtility.load(scene.getWindow());
//			Game loaded = XMLManager.loadGame(loadFile);
//			setGame(loaded);
//		} catch (GameFileException e) {
//			System.out.println("Unable to load game");
//		}
//	}

	public Workspace getWorkspace() {
		return w;
	}

	public GridPane getDefaultPane() {
		return myPanes.get(DEFAULT_MAP_PANE_INDEX);
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		Mail mail = (Mail) arg;		
		forward(mail.getPath(), (Mail) arg);
	}

	@Override
	public Deque<String> getAnscestralPath() {
		return null;
	}

	@Override
	public void process(Mail mail) {
		// Do nothing for now
	}

	@Override
	public Anscestral getChild(String id) {
		return w;
	}
}
