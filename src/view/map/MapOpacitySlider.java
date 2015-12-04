package view.map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class MapOpacitySlider extends AbstractMapSlider {

	public MapOpacitySlider(MiniMap minimap, Double sliderSize) {
		findResources();
		theMiniMap = minimap;
		sliderElements = new GridPane();
		theSlider = new Slider();
		theSlider.setPrefWidth(sliderSize);
	}


	protected void setUpSliderListener() {
		theSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				// Create a scaled transform that resizes everything in the
				// map's zoomGroup
				// 1.0 means no change to the scale
				double convertedOpacityValue = ((double) new_val) / 100.0;

				// Update the text of the label accordingly
				value.setText(String.format("%.2f", new_val));

				theMiniMap.updateMiniMapOpacity(convertedOpacityValue);
			}
		});
	}
}
