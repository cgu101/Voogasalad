package view.element;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import view.controlbar.ControlBarCreator;
import view.screen.*;

public class Icons extends AbstractElement {

	private AbstractScreen currentScreen;
	private Text selector;
	private Icon selected;

	public Icons(GridPane pane, AbstractScreen screen) {
		super(pane);
		findResources();
		this.currentScreen = screen;
		makePane();
	}

	@Override
	protected void makePane() {
		selector = new Text("");
		selector.setFont(headerFont);
		GridPane textPane = new GridPane();
		textPane.add(selector, 0, 0);
		GridPane iconPane = new GridPane();
		pane.add(textPane, 0, 0);
		ArrayList<Icon> iconList = new ArrayList<Icon>();
		String[] iconNames = myResources.getString("iconNames").split(",");
		int i = 1;
		for (String s : iconNames) {
			Icon toAdd = new Icon(s, iconNames.length);
			iconList.add(toAdd);
			iconPane.add(toAdd, i++, 0);
		}
		iconPane.setHgap(Double.parseDouble(myResources.getString("hgap")));
		pane.add(iconPane, 0, 1);
		pane.setVgap(Double.parseDouble(myResources.getString("vgap")));
	}

	private class Icon extends Button {
		private ImageView image;
		private String iconName;

		private Icon(String s, int numberOfIcons) {
			Image img = new Image(getClass().getClassLoader().getResourceAsStream(myResources.getString(s)));
			image = new ImageView(img);
			image.setFitWidth(Double.parseDouble(myResources.getString("width")));
			image.setPreserveRatio(true);
			image.setSmooth(true);
			image.setCache(true);
			setGraphic(image);
			this.setOpacity(0.5);
			iconName = s;
			this.setOnAction(e -> this.handleAction());
			this.setOnMouseEntered(e -> this.handleHover());
			this.setOnMouseExited(e -> this.handleExit());
			this.setFocusTraversable(false);
		}

		private void handleAction() {
			if (selected == this) {
				try {

					/**
					 * TODO: David, talk to me about this... this is TEMPORARY!
					 */
					if (testIfCreator(iconName)) { ControlBarCreator creatorController = new ControlBarCreator(); currentScreen.setNextScreen(creatorController.getScreen()); return; };

					Class<?> c = Class.forName("view.screen." + iconName + "Screen");
					currentScreen.setNextScreen((AbstractScreen) c.newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				if (selected != null) {
					selected.setFocused(false);
				}
				selected = this;
				this.setFocused(true);
			}
		}

		private void handleHover() {
			selector.setText(iconName);

			FadeTransition fadeTransition = 
					new FadeTransition(Duration.millis(300), this);
			fadeTransition.setFromValue(0.5);
			fadeTransition.setToValue(1.0);
			fadeTransition.setCycleCount(1);
			fadeTransition.setAutoReverse(true);
			fadeTransition.play();
			
			ScaleTransition scaleTransition = 
					new ScaleTransition(Duration.millis(300), this);
			scaleTransition.setToX(1.1);
			scaleTransition.setToY(1.1);
			scaleTransition.setCycleCount(1);
			scaleTransition.setAutoReverse(true);

			ParallelTransition parallelTransition = new ParallelTransition();
			parallelTransition.getChildren().addAll(
					fadeTransition,
					scaleTransition
					);
			parallelTransition.setCycleCount(1);
	        parallelTransition.play();
		}

		private void handleExit() {
			if (selected == null) {
				selector.setText("");
			} else {
				selector.setText(selected.iconName);
			}

			FadeTransition fadeTransition = 
					new FadeTransition(Duration.millis(300), this);
			fadeTransition.setFromValue(1.0);
			fadeTransition.setToValue(0.5);
			fadeTransition.setCycleCount(1);
			fadeTransition.setAutoReverse(true);
			
			ScaleTransition scaleTransition = 
					new ScaleTransition(Duration.millis(300), this);
			scaleTransition.setToX(1);
			scaleTransition.setToY(1);
			scaleTransition.setCycleCount(1);
			scaleTransition.setAutoReverse(true);

			ParallelTransition parallelTransition = new ParallelTransition();
			parallelTransition.getChildren().addAll(
					fadeTransition,
					scaleTransition
					);
			parallelTransition.setCycleCount(1);
	        parallelTransition.play();
		}

		private boolean testIfCreator (String s) {
			return s.equals("Creator");
		}
	}
}
