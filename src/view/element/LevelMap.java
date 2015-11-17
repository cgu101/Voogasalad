package view.element;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.screen.AbstractScreen;

public class LevelMap extends AbstractElement {
	private Tab myTab;
	private Map map;
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
		makePane();
	}

	public Tab getTab() {
		return myTab;
	}

	@Override
	protected void makePane() {
//		Test Narnia map image
		backgroundImage = new Image("http://www.narniaweb.com/wp-content/uploads/2009/08/NarniaMap.jpg");
		
		map.addMapElements(new ImageView(backgroundImage));
		map.createGroups();
		map.createScrollPane();
				
		//Tests to figure out ScrollPane issue		
//		sp = new ScrollPane();
//		StackPane layout = new StackPane();
//		layout.getChildren().addAll(new ImageView(backgroundImage));
//		
//		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//		sp.setPannable(true);
//		
//		sp.prefWidthProperty().bind(screen.getScene().widthProperty());
//		sp.prefHeightProperty().bind(screen.getScene().heightProperty());
//		
//		sp.setContent(layout);
		
		pane.add(map.getTheMap(), 0, 0);
	}

	public ScrollPane getMap() {
		//There is a problem with this method. Nothing shows up on screen
		//When it returns something. Suspect it has to do with the bound
		//Width and Height.
		
		
		return null;
		//return map.getTheMap();
	}

}
