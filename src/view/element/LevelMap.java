package view.element;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import view.screen.AbstractScreen;

public class LevelMap extends AbstractElement {
	private Tab myTab;
	private Map map;
	private MapZoomSlider slider;
	private AbstractScreen screen;
	private Image backgroundImage;
	private ScrollPane sp;

	public LevelMap(GridPane pane, int i, AbstractScreen screen) {
		super(pane);
		findResources();
		myTab = new Tab("Level " + (i + 1));
		myTab.setContent(pane);
		myTab.setId(Integer.toString(i));
		this.screen = screen;
		map = new Map(screen);
		slider = new MapZoomSlider(map, Double.valueOf(myResources.getString("sliderwidth")));
		makePane();
	}

	public Tab getTab() {
		return myTab;
	}

	@Override
	protected void makePane() {
		//Test Narnia map image
		backgroundImage = new Image("http://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg");
		
		//Add any elements you want to appear on the map using this method
		map.addMapElements(new ImageView(backgroundImage));
		
		//Create the map after adding elements you want
		map.createTheMap();
		
		//Create the slider for the map after the map has been defined
		slider.createTheSlider();
		
		//Add everything to the column in the parent GridPane
		pane.add(map.getTheMap(), 0, 0);
		pane.add(slider.getTheSlider(), 0, 1);
	}

	public ScrollPane getMap() {
		//There is a problem with this method. Nothing shows up on screen
		//When it returns something. Suspect it has to do with the bound
		//Width and Height.
		
		return null;
		//return map.getTheMap();
	}

}
