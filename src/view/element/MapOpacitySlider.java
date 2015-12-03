package view.element;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import view.visual.AbstractVisual;

public class MapOpacitySlider extends AbstractVisual {
	private GridPane sliderElements;
	private Slider theSlider;
	private Label opacityValue;
	private Label opacityCaption;
	private MiniMap theMiniMap;
	
	public MapOpacitySlider(MiniMap minimap, Double sliderSize) {
		theMiniMap = minimap;
		sliderElements = new GridPane();
		theSlider = new Slider();
		theSlider.setPrefWidth(sliderSize);
	}
	
	public void createTheSlider() {
		initializeSlider();
		createLabels();
		setUpSliderListener();
		fillGridPane();
	}
	
	private void createLabels() {
		//Labels that are displayed alongside the slider bar
		opacityCaption = new Label("Opacity Level: ");
		opacityValue = new Label(Double.toString(theSlider.getValue()));
	}
	
	private void initializeSlider() {
		theSlider.setMin(10);
		theSlider.setMax(100);
		theSlider.setValue(50);
		theSlider.setShowTickLabels(true);
		theSlider.setShowTickMarks(true);
		theSlider.setMajorTickUnit(25);
		theSlider.setMinorTickCount(4);
	}
	
	private void setUpSliderListener() {
		theSlider.valueProperty().addListener(new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> ov, 
					Number old_val, Number new_val) {
				//Create a scaled transform that resizes everything in the map's zoomGroup
				//1.0 means no change to the scale
				double convertedOpacityValue = ((double) new_val)/ 100.0;
				
				//Update the text of the label accordingly
				opacityValue.setText(String.format("%.2f", new_val));
				
				theMiniMap.updateMiniMapOpacity(convertedOpacityValue);
			}
		});
	}
	
	private void fillGridPane() {
		//Set the horizontal gap between grid elements, and the outside padding of the GridPane
		sliderElements.setHgap(30);
		sliderElements.setPadding(new Insets(10, 10, 10, 10));
		
		//Set the positions of the different GridPane elements
		GridPane.setConstraints(opacityCaption, 0, 0);
		GridPane.setConstraints(opacityValue, 1, 0);
		GridPane.setConstraints(theSlider, 2, 0);
		
		//Add all the elements to the GridPane
		sliderElements.getChildren().addAll(opacityCaption, opacityValue, theSlider);
	}
	
	public GridPane getSliderWithCaptions() {
		return sliderElements;
	}
}
