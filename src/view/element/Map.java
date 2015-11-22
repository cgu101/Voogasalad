package view.element;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import view.screen.AbstractScreen;
/**
 * The Map class allows for a visual representation of the game map. Includes a 
 * ScrollPane for the map's display area, and a Slider for zooming in and out of the ScrollPane
 * contents. Allows for the addition and removal of Actor nodes within the display area.
 * @author Daniel
 *
 */
public class Map extends AbstractElement {

	private Group contentGroup;
	private Group zoomGroup;
	private Group layout;
	private AbstractScreen screen;

	protected ScrollPane mapArea;
	private MapZoomSlider sliderArea;
	private MapActorManager actorManager;
	protected ImageView background;

	
	/**
	 * Creates a Map for the visual representation of the game
	 * map on the GUI, with an included zoom slider. This constructor will display this map within the GridPane you 
	 * specify.
	 * @param pane - GridPane on which to display this map
	 * @param screen - screen associated with this map
	 */

//	public Map(GridPane pane, AbstractScreen screen) {
	public Map(GridPane pane) {
		super(pane);
		findResources();
		// Use a StackPane so we can layer things on top of one another, like an
		// actor
		// over a background tile. Note that you can probably use something else
		// if a
		// StackPane is not appropriate, such as a Canvas.
		layout = new Group();
		mapArea = new ScrollPane();

		// The actorManager needs access to the layout so it can place actors on
		// it
		actorManager = new MapActorManager(layout);

		makePane();
	}

	/**
	 * Adds a Node onto the Map's display area at the given x and y coordinates.
	 * @param element - Node to add to the map
	 * @param x	- horizontal position to place the Node
	 * @param y - vertical position to place the Node
	 */
	public void addActor(Node element, double x, double y) {
		// Use this method to add an actor to the StackPane.
		actorManager.addActor(element, x, y);
	}
	
	/**
	 * Remove a Node from the Map's display area.
	 * @param element - the Node to be removed
	 */
	public void removeActor(Node element) {
		actorManager.removeActor(element);
	}
	
	/**
	 * Update the Map's background to the Node in the parameter.
	 * @param background - the Node to set as the new Map background
	 */
	public void updateBackground(Image background) {
		ImageView bg = new ImageView(background);
		actorManager.updateBackground(bg);
		bg.fitWidthProperty().bind(pane.widthProperty());
		bg.setPreserveRatio(true);
	}

	public Group getGroup() {
		return layout;
	}

	/**
	 * Creates both of the elements of the map to be displayed on the GUI: a ScrollPane on which 
	 * to display the background and actors, and a Slider to allow for zooming in the ScrollPane.
	 */
	public void createTheMap() {
		createMapArea();

		// The slider needs access to the zoomGroup so it can resize it when it
		// gets dragged
		sliderArea = new MapZoomSlider(zoomGroup, Double.valueOf(myResources.getString("sliderwidth")));
		sliderArea.createTheSlider();
	}

	
	/**
	 * Adds the Map's display area and associated zoom slider to the specified GridPane.
	 * @param pane - the GridPane on which to add the Map and its zoom slider
	 */
	private void addMapToPane(GridPane pane) {
		pane.add(mapArea, 1, 0);
		pane.add(sliderArea.getTheSlider(), 1, 1);
	}
	/**
	 * Creates  a ScrollPane to display the background and all Actor nodes,
	 * and the Groups in which Actor nodes can be placed.
	 */
	private void createMapArea() {
		// Creates the ScrollPane where all the map elements will be displayed
		createGroups();
		createMapScrollPane();
	}
	
	private void createMapScrollPane() {
		// Hide the vertical and horizontal scrollbars, make the pane pannable
		mapArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mapArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mapArea.setPannable(true);

		// Bind the preferred size of the scroll area to the size of the scene
//		mapArea.prefWidthProperty().bind(screen.getScene().widthProperty());
//		mapArea.prefHeightProperty().bind(screen.getScene().heightProperty());

		mapArea.setContent(contentGroup);
	}

	private void createGroups() {
		// Groups all the nodes that will appear on the map and allows them to
		// be collectively rescaled using a Scale.
		zoomGroup = new Group();

		// Contains the zoomGroup that will then be displayed on the map
		contentGroup = new Group();

		contentGroup.getChildren().add(zoomGroup);
		zoomGroup.getChildren().add(layout);
	}
	
	/** 
	 * Scales all the Nodes within the zoomGroup by the given Scale.
	 * @param scaleTransform
	 */
	public void setMapTransform(Scale scaleTransform) {
		zoomGroup.getTransforms().clear();
		zoomGroup.getTransforms().add(scaleTransform);
	}

	private void addEventFilters() {
		mapArea.addEventFilter(KeyEvent.ANY, e -> {
			e.consume();
		});
		sliderArea.getTheSlider().addEventFilter(KeyEvent.ANY, e -> {
			e.consume();
		});
	}

	@Override
	protected void makePane() {
		Image backgroundImage = new Image(myResources.getString("backgroundURL"));

		// Test white rectangle
		Rectangle test = new Rectangle(100, 100);
		test.setFill(Color.GRAY);

		// Add any elements you want to appear on the map using this method
		
		addActor(test, 0, 0);
		updateBackground(backgroundImage);

		// Create the map after adding elements you want
		createTheMap();

		// remove pesky key event handlers
		addEventFilters();

		// Add the map to the GridPane
		addMapToPane(pane);

	}
}
