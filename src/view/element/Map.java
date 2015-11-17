package view.element;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import view.screen.AbstractScreen;

public class Map {
	private AbstractScreen screen;
	private Group contentGroup;
	private Group zoomGroup;
	
	private StackPane layout;
	private ScrollPane theMap;
	
	
	public Map(AbstractScreen screen) {
		this.screen = screen;
		
		//Use a StackPane so we can layer things on top of one another, like an actor
		//over a background tile. Note that you can probably use something else if a
		//StackPane is not appropriate, such as a Canvas.
		layout = new StackPane();
		theMap = new ScrollPane();
	}
	
	public void addMapElements(Node... elements) {
		layout.getChildren().addAll(elements);
	}
	
	public void createTheMap() {
		createGroups();
		createScrollPane();
	}
	
	private void createScrollPane() {
		theMap = new ScrollPane();
		
		//Hide the vertical and horizontal scrollbars, make the pane pannable
		theMap.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		theMap.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		theMap.setPannable(true);
		
		//Bind the preferred size of the scroll area to the size of the scene
		theMap.prefWidthProperty().bind(screen.getScene().widthProperty());
		theMap.prefHeightProperty().bind(screen.getScene().heightProperty());

		theMap.setContent(contentGroup);
	}
	
	private void createGroups() {
		//Groups all the nodes that will appear on the map and allows them to be 
		//collectively rescaled using a Scale.
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
	
	public ScrollPane getTheMap() {
		return theMap;
	}
	
	public Group getZoomGroup() {
		return zoomGroup;
	}

}
