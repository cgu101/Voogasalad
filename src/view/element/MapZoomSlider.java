package view.element;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import view.map.MiniMap;
import view.visual.AbstractVisual;

/**
 * The MapZoomSlider class creates a Slider associated with a Map class, and allows for zooming
 * in and out of the Map's ScrollPane area. The Slider is composed of three Nodes contained within
 * a GridPane: a Slider, and two Labels for showing relevant information.
 * @author Daniel
 *
 */

public class MapZoomSlider extends AbstractVisual {

	private GridPane sliderElements;
	private Slider theSlider;
	private Label scaleValue;
	private Label scaleCaption;
	private Group mapZoomGroup;
	private MiniMap theMiniMap;
	
	
	public MapZoomSlider(Group zoomGroup, MiniMap minimap, Double sliderSize) {
		sliderElements = new GridPane();
		theSlider = new Slider();
		theSlider.setPrefWidth(sliderSize); //The size of the slider is set inside the resource file
		mapZoomGroup = zoomGroup;
		theMiniMap = minimap;
		findResources();
	}
	
	public void createTheSlider() {
		initializeSlider();
		createLabels();
		setUpSliderListener();
		fillGridPane();
	}
	
	private void createLabels() {
		//Labels that are displayed alongside the slider bar
		scaleCaption = new Label("Zoom Amount: ");
		scaleValue = new Label(Double.toString(theSlider.getValue()));
	}
	
	private void initializeSlider() {		
		theSlider.setMin(Double.valueOf(myResources.getString("slidermin")));
		theSlider.setMax(Double.valueOf(myResources.getString("slidermax")));
		theSlider.setValue(Double.valueOf(myResources.getString("startingvalue")));
		theSlider.setShowTickLabels(true);
		theSlider.setShowTickMarks(true);
		theSlider.setMajorTickUnit(Double.valueOf(myResources.getString("majortickunit")));
		theSlider.setMinorTickCount(Integer.valueOf(myResources.getString("minortickcount")));
	}
	
	private void setUpSliderListener() {
		theSlider.valueProperty().addListener(new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> ov, 
					Number old_val, Number new_val) {
				//Create a scaled transform that resizes everything in the map's zoomGroup
				//1.0 means no change to the scale
				double convertedScaleValue = (((double) new_val) + 100.0) / 100.0;
				Scale scaleTransform = new Scale(convertedScaleValue, convertedScaleValue, 0, 0);
				
				//Transform everything inside the zoomGroup according to the scale
				mapZoomGroup.getTransforms().clear();
				mapZoomGroup.getTransforms().add(scaleTransform);
				
				//Update the text of the label accordingly
				scaleValue.setText(String.format("%.2f", new_val));
				
				theMiniMap.updateMiniMapRectangleOnZoom(convertedScaleValue);
			}
		});
	}
	
	private void fillGridPane() {
		//Set the horizontal gap between grid elements, and the outside padding of the GridPane
		sliderElements.setHgap(Double.valueOf(myResources.getString("elementspacing")));
		double paddingValue = Double.valueOf(myResources.getString("padding"));
		sliderElements.setPadding(new Insets(paddingValue, paddingValue, paddingValue, paddingValue));
		
		//Set the positions of the different GridPane elements
		GridPane.setConstraints(scaleCaption, 0, 0);
		GridPane.setConstraints(scaleValue, 1, 0);
		GridPane.setConstraints(theSlider, 2, 0);
		
		//Add all the elements to the GridPane
		sliderElements.getChildren().addAll(scaleCaption, scaleValue, theSlider);
	}
	
	public GridPane getSliderWithCaptions() {
		return sliderElements;
	}
	
	public Slider getSlider() {
		return theSlider;
	}
}
