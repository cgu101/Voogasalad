package view.map;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import view.visual.AbstractVisual;

public abstract class AbstractMapSlider extends AbstractVisual {
	protected GridPane sliderElements;
	protected Slider theSlider;
	protected Label value;
	protected Label caption;
	protected MiniMap theMiniMap;

	public void createTheSlider() {
		initializeSlider();
		createLabels();
		setUpSliderListener();
		fillGridPane();
	}

	private void fillGridPane() {
		// Set the horizontal gap between grid elements, and the outside padding
		// of the GridPane
		sliderElements.setHgap(Double.parseDouble(myResources.getString("elementspacing")));
		sliderElements.setPadding(createSliderInsets(Double.parseDouble(myResources.getString("padding"))));

		// Set the positions of the different GridPane elements
		GridPane.setConstraints(caption, 0, 0);
		GridPane.setConstraints(value, 1, 0);
		GridPane.setConstraints(theSlider, 2, 0);

		// Add all the elements to the GridPane
		sliderElements.getChildren().addAll(caption, value, theSlider);
	}
	
	private Insets createSliderInsets(double spacing) {
		return new Insets(spacing, spacing, spacing, spacing);
	}

	private void createLabels() {
		// Labels that are displayed alongside the slider bar
		caption = new Label(myResources.getString("name"));
		value = new Label(Double.toString(theSlider.getValue()));
	}

	public GridPane getSliderWithCaptions() {
		return sliderElements;
	}

	public Slider getSlider() {
		return theSlider;
	}

	private void initializeSlider() {
		theSlider.setMin(Double.parseDouble(myResources.getString("slidermin")));
		theSlider.setMax(Double.parseDouble(myResources.getString("slidermax")));
		theSlider.setValue(Double.parseDouble(myResources.getString("startingvalue")));
		theSlider.setShowTickLabels(true);
		theSlider.setShowTickMarks(true);
		theSlider.setMajorTickUnit(Double.parseDouble(myResources.getString("majortickunit")));
		theSlider.setMinorTickCount(Integer.parseInt(myResources.getString("minortickcount")));
	}

	protected abstract void setUpSliderListener();
}
