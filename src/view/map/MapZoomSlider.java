package view.map;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;
import view.visual.AbstractVisual;

/**
 * The MapZoomSlider class creates a Slider associated with a Map class, and
 * allows for zooming in and out of the Map's ScrollPane area. The Slider is
 * composed of three Nodes contained within a GridPane: a Slider, and two Labels
 * for showing relevant information.
 * 
 * @author Daniel, David
 *
 */

public class MapZoomSlider extends AbstractMapSlider {

	private Group mapZoomGroup;

	public MapZoomSlider(Group zoomGroup, MiniMap minimap, Double sliderSize) {
		findResources();
		sliderElements = new GridPane();
		theSlider = new Slider();
		theSlider.setPrefWidth(sliderSize); // The size of the slider is set
											// inside the resource file
		mapZoomGroup = zoomGroup;
		theMiniMap = minimap;
	}

	protected void setUpSliderListener() {
		theSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				// Create a scaled transform that resizes everything in the
				// map's zoomGroup
				// 1.0 means no change to the scale
				double convertedScaleValue = (((double) new_val) + 100.0) / 100.0;
				Scale scaleTransform = new Scale(convertedScaleValue, convertedScaleValue, 0, 0);

				// Transform everything inside the zoomGroup according to the
				// scale
				mapZoomGroup.getTransforms().clear();
				mapZoomGroup.getTransforms().add(scaleTransform);

				// Update the text of the label accordingly
				value.setText(String.format("%.2f", new_val));

				theMiniMap.updateMiniMapRectangleOnZoom(convertedScaleValue);
			}
		});
	}
}
