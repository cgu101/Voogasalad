package view.element;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import view.screen.AbstractScreen;

public class Map extends AbstractElement {
	
	private Group contentGroup;
	private Group zoomGroup;
	private Group layout;
	private AbstractScreen screen;

	private ScrollPane mapArea;
	private MapZoomSlider slider;
	private MapActorManager actorManager;

	public Map(GridPane pane, AbstractScreen screen) {
		super(pane);
		findResources();
		this.screen = screen;
		//Use a StackPane so we can layer things on top of one another, like an actor
		//over a background tile. Note that you can probably use something else if a
		//StackPane is not appropriate, such as a Canvas.
		layout = new Group();
		mapArea = new ScrollPane();
		
		//The actorManager needs access to the layout so it can place actors on it
		actorManager = new MapActorManager(layout);
		
		makePane();
	}
	

	public void addActor(Node element, double x, double y) {
		//Use this method to add an actor to the StackPane.
		actorManager.addActor(element, x, y);
	}
	
	public Group getGroup(){
		return layout;
	}

	public void createTheMap() {
		createMapArea();
		
		//The slider needs access to the zoomGroup so it can resize it when it gets dragged
		slider = new MapZoomSlider(zoomGroup, Double.valueOf(myResources.getString("sliderwidth")));
		slider.createTheSlider();
	}
	
	public void addMapToPane(GridPane pane) {
		pane.add(mapArea, 1, 0);
		pane.add(slider.getTheSlider(), 1, 1);
	}

	private void createMapArea() {
		//Creates the ScrollPane where all the map elements will be displayed
		createGroups();
		createMapScrollPane();
	}

	private void createMapScrollPane() {
		//Hide the vertical and horizontal scrollbars, make the pane pannable
		mapArea.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mapArea.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mapArea.setPannable(true);

		//Bind the preferred size of the scroll area to the size of the scene
		mapArea.prefWidthProperty().bind(screen.getScene().widthProperty());
		mapArea.prefHeightProperty().bind(screen.getScene().heightProperty());

		mapArea.setContent(contentGroup);
	}

	private void createGroups() {
		//Groups all the nodes that will appear on the map and allows them to
		//be collectively rescaled using a Scale.
		zoomGroup = new Group();

		//Contains the zoomGroup that will then be displayed on the map
		contentGroup = new Group();
		
		contentGroup.getChildren().add(zoomGroup);
		zoomGroup.getChildren().add(layout);
	}

	public void setMapTransform(Scale scaleTransform) {
		zoomGroup.getTransforms().clear();
		zoomGroup.getTransforms().add(scaleTransform);
	}

	private void addEventFilters () {
		mapArea.addEventFilter(KeyEvent.ANY, e -> {e.consume();});
		slider.getTheSlider().addEventFilter(KeyEvent.ANY, e -> {e.consume();});
	}
	
	@Override
	protected void makePane() {
		//Test Narnia map image
		Image backgroundImage = new Image("http://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg");
		ImageView background = new ImageView(backgroundImage);
		background.fitWidthProperty().bind(pane.widthProperty());
		background.setPreserveRatio(true);
		
		//Test white rectangle
		Rectangle test = new Rectangle(200, 200);
		test.setFill(Color.WHITE);

		//Add any elements you want to appear on the map using this method
		addActor(background, 0, 0);
		addActor(test, 0, 0);
		
		//Create the map after adding elements you want
		createTheMap();
		
		// remove pesky key event handlers
		addEventFilters();
		
		//Add the map to the GridPane
		addMapToPane(pane);
		
	}
}
