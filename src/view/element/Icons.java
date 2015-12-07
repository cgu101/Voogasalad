package view.element;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
		}

		private void handleExit() {
			if (selected == null) {
				selector.setText("");
			} else {
				selector.setText(selected.iconName);
			}
		}
		
		private boolean testIfCreator (String s) {
			return s.equals("Creator");
		}
	}
}
