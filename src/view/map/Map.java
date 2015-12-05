package view.map;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import view.element.AbstractElement;
import view.handler.ActorHandler;

/**
 * The Map class allows for a visual representation of the game map. Includes a
 * ScrollPane for the map's display area, and a Slider for zooming in and out of
 * the ScrollPane contents. Allows for the addition and removal of Actor nodes
 * within the display area.
 * 
 * @author Daniel
 *
 */
public class Map extends AbstractElement {

	private Group contentGroup;
	private Group zoomGroup;
	private Group layout;
	protected AuthoringController controller;

	private Group mapArea;
	protected ScrollPane mapScrollableArea;
	private MapZoomSlider zoomSliderArea;
	private MapOpacitySlider opacitySliderArea;
	private MinimapResizerSlider miniMapResizerSliderArea;
	private MiniMap theMiniMap;
	protected ImageView background;

	private double mapRegularWidth;
	private double mapRegularHeight;
	private boolean preserveMapRatio;

	private ToolBar editToolbar;
	private ActorHandler actorHandler;

	/**
	 * Creates a Map for the visual representation of the game map on the GUI,
	 * with an included zoom slider. This constructor will display this map
	 * within the GridPane you specify.
	 * 
	 * @param pane
	 *            - GridPane on which to display this map
	 * @param screen
	 *            - screen associated with this map
	 */

	public Map(GridPane pane) {
		super(pane);
		findResources();

		layout = new Group();
		mapScrollableArea = new ScrollPane();
		mapArea = new Group();

		mapRegularWidth = Double.valueOf(myResources.getString("regularmapwidth"));
		mapRegularHeight = Double.valueOf(myResources.getString("regularmapheight"));
		preserveMapRatio = Boolean.valueOf(myResources.getString("preservemapratio"));

		// The actorManager needs access to the layout so it can place actors on
		// it
		controller = new AuthoringController();
		editToolbar = new ToolBar();
		//actorHandler = new ActorHandler(layout, controller, editToolbar, this, theMiniMap, zoomSliderArea);
		// TODO: actorHandler = new ActorHandler(layout, zoomSliderArea,
		// controller, editToolbar);

		makePane();
	}

	/**
	 * Adds a Node onto the Map's display area at the given x and y coordinates.
	 * 
	 * @param element
	 *            - Node to add to the map
	 * @param x
	 *            - horizontal position to place the Node
	 * @param y
	 *            - vertical position to place the Node
	 */
	public void addActor(Actor element, double x, double y) {
		// Use this method to add an actor to the StackPane.
		actorHandler.addActor(element, x, y);
	}

	/**
	 * Remove a Node from the Map's display area.
	 * 
	 * @param element
	 *            - the Node to be removed
	 */
	public void removeActor(Node element) {
		actorHandler.removeActor(element);
	}

	/**
	 * Update the Map's background to the Node in the parameter.
	 * 
	 * @param background
	 *            - the Node to set as the new Map background
	 */
	public void initializeBackground(Image bg) {
		background = new ImageView(bg);
		background.setFitWidth(mapRegularWidth);
		background.setSmooth(true);
		background.setCache(true);
		if (!preserveMapRatio) {
			background.setPreserveRatio(false);
			background.setFitHeight(mapRegularHeight);
		}
		background.setPreserveRatio(true);
	}
	
	public void updateBackground() {
		actorHandler.updateBackground(background);
	}

	public void setPanEnabled(boolean enable) {
		mapScrollableArea.setPannable(enable);
	}

	public Group getGroup() {
		return layout;
	}

	/**
	 * Creates both of the elements of the map to be displayed on the GUI: a
	 * ScrollPane on which to display the background and actors, and a Slider to
	 * allow for zooming in the ScrollPane.
	 */
	public void createTheMap() {
		createMapArea();

		// The slider needs access to the zoomGroup so it can resize it when it
		// gets dragged

		zoomSliderArea = new MapZoomSlider(zoomGroup, theMiniMap, Double.valueOf(myResources.getString("sliderwidth")));
		zoomSliderArea.createTheSlider();

		opacitySliderArea = new MapOpacitySlider(theMiniMap, Double.valueOf(myResources.getString("sliderwidth")));
		opacitySliderArea.createTheSlider();
		
		miniMapResizerSliderArea = new MinimapResizerSlider(theMiniMap, Double.valueOf(myResources.getString("sliderwidth")));
		miniMapResizerSliderArea.createTheSlider();
	}

	public GridPane getZoomSlider() {
		return zoomSliderArea.getSliderWithCaptions();
	}

	public GridPane getOpacitySlider() {
		return opacitySliderArea.getSliderWithCaptions();
	}

	public GridPane getResizerSlider() {
		return miniMapResizerSliderArea.getSliderWithCaptions();
	}

	public ToolBar getToolbar() {
		return editToolbar;
	}

	/**
	 * Adds the Map's display area and associated zoom slider to the specified
	 * GridPane.
	 * 
	 * @param pane
	 *            - the GridPane on which to add the Map and its zoom slider
	 */
	private void addMapToPane(GridPane pane) {
		pane.add(mapArea, 0, 0);

		// pane.add(zoomSliderArea.getSliderWithCaptions(), 0, 1);
		// pane.add(opacitySliderArea.getSliderWithCaptions(), 0, 2);

	}

	/**
	 * Creates a ScrollPane to display the background and all Actor nodes, and
	 * the Groups in which Actor nodes can be placed.
	 */
	private void createMapArea() {
		// Creates the ScrollPane where all the map elements will be displayed
		createGroups();
		createMapScrollPane();
		createPanListeners();
		createMiniMap();

		mapArea.getChildren().add(mapScrollableArea);
		mapArea.getChildren().add(theMiniMap.getMiniMap());
		System.out.println("The minimap's bounds are: " + theMiniMap.getMiniMap().getBoundsInParent());
	}

	private void createMapScrollPane() {
		// Hide the vertical and horizontal scrollbars, make the pane pannable
		mapScrollableArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mapScrollableArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mapScrollableArea.setPannable(true);

		mapScrollableArea.setContent(contentGroup);

		// mapScrollableArea.prefWidthProperty().bind(pane.widthProperty());
		// mapScrollableArea.prefViewportWidthProperty().bind(pane.widthProperty());
		mapScrollableArea.setPrefWidth(mapRegularWidth);
		mapScrollableArea.setPrefViewportWidth(mapRegularWidth);

		// mapScrollableArea.prefViewportHeightProperty().bind(pane.heightProperty());
		mapScrollableArea.setPrefHeight(mapRegularHeight);
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

	private void createMiniMap() {
		theMiniMap = new MiniMap(background, mapScrollableArea, mapRegularWidth, mapRegularHeight);
	}

	private void addEventFilters() {
		mapScrollableArea.addEventFilter(KeyEvent.ANY, e -> {
			e.consume();
		});
		zoomSliderArea.getSliderWithCaptions().addEventFilter(KeyEvent.ANY, e -> {
			e.consume();
		});
	}

	private void createPanListeners() {
		mapScrollableArea.vvalueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				theMiniMap.updateMiniMapRectangleOnVerticalPan(new_val.doubleValue());
			}

		});

		mapScrollableArea.hvalueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				theMiniMap.updateMiniMapRectangleOnHorizontalPan(new_val.doubleValue());
			}

		});
	}

	@Override
	protected void makePane() {
		//Image backgroundImage = new Image(myResources.getString("backgroundURL"));
		Image backgroundImage = new Image("http://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg");
		initializeBackground(backgroundImage);

		// Create the map after adding elements you want
		createTheMap();
		
		actorHandler = new ActorHandler(layout, controller, editToolbar, this, theMiniMap, zoomSliderArea);

		updateBackground();

		// remove pesky key event handlers
		addEventFilters();

		// Add the map to the GridPane
		addMapToPane(pane);
		System.out.println("The background bounds are: " + background.getBoundsInParent());
		// System.out.println(mapScrollableArea.getHvalue());
		// System.out.println(mapScrollableArea.getVvalue());
	}
}
