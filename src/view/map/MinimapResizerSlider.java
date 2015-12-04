package view.map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class MinimapResizerSlider extends AbstractMapSlider {

	public MinimapResizerSlider(MiniMap minimap, Double sliderSize) {
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

				// Update the text of the label accordingly
				value.setText(String.format("%.2f", new_val));

				theMiniMap.updateMiniMapSize((double) new_val);
			}
		});
	}

}
