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
		sliderElements.setHgap(30);
		sliderElements.setPadding(new Insets(10, 10, 10, 10));

		// Set the positions of the different GridPane elements
		GridPane.setConstraints(caption, 0, 0);
		GridPane.setConstraints(value, 1, 0);
		GridPane.setConstraints(theSlider, 2, 0);

		// Add all the elements to the GridPane
		sliderElements.getChildren().addAll(caption, value, theSlider);
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
		theSlider.setMin(Double.parseDouble(myResources.getString("min")));
		theSlider.setMax(Double.parseDouble(myResources.getString("max")));
		theSlider.setValue(Double.parseDouble(myResources.getString("value")));
		theSlider.setShowTickLabels(true);
		theSlider.setShowTickMarks(true);
		theSlider.setMajorTickUnit(Double.parseDouble(myResources.getString("tick")));
		theSlider.setMinorTickCount(Integer.parseInt(myResources.getString("count")));
	}

	protected abstract void setUpSliderListener();
}
