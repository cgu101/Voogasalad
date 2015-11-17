package view.element;

import authoring.controller.constructor.LevelConstructor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.screen.AbstractScreen;

public class LevelMap extends Map {
	private Tab myTab;
	private AbstractScreen screen;
	private Image backgroundImage;
	private ScrollPane sp;
	private LevelConstructor constructor;


	public LevelMap(GridPane pane, int i, AbstractScreen screen, LevelConstructor lc) {
		super(pane, screen);
		myTab = new Tab("Level " + (i + 1));
		myTab.setContent(pane);
		myTab.setId(Integer.toString(i));	
		constructor = lc;
		makePane();
	}

	public Tab getTab() {
		return myTab;
	}

	@Override
	protected void makePane() {
		//Test Narnia map image
		backgroundImage = new Image("http://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg");
		
		//Test white rectangle
		Rectangle test = new Rectangle(640, 480);
		test.setFill(Color.WHITE);
		
		//Add any elements you want to appear on the map using this method
		addMapElements(test);
		
		//Create the map after adding elements you want
		createTheMap();
	}
	
}
