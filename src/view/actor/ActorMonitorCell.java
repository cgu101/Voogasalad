package view.actor;

import java.util.List;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import player.controller.PlayerController;

public class ActorMonitorCell extends AbstractListCell {

	private PlayerController controller;
	
	/**
	 * ActorMonitorCell Constructor
	 * This method creates a new instance of an ActorMonitorCell
	 *
	 * @param  actor The Actor that will have its properties displayed.
	 * @param  controller The PlayerController which allows the ActorMonitorCell to grab the actors' properties
	 */
	public ActorMonitorCell(PlayerController controller) {
		findResources();
		this.controller = controller;
	}

	//TODO: Make a non-default image
	private VBox makeImage(String pic, String name) {
		VBox v = new VBox();
		ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(pic)));
		image.setFitHeight(Double.parseDouble(myResources.getString("imagesize")));
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);
		GridPane.setRowSpan(image, 1);
		v.getChildren().addAll(new Label(name), image);
		return v;
	}
	
	@Override
	protected void makeCell(String item) {
		System.out.println(item);
		//item is the actor "name" (Group name?)
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		
		Map<String, String> propertyStringMap = controller.getPropertyStringMapFromActorString(item);
		box.getChildren().add(makeImage(propertyStringMap.get("image"), item));
		box.getChildren().add(makePropertiesVBox(propertyStringMap));
		
		setGraphic(box);
	} 	
	
	protected VBox makePropertiesVBox(Map<String, String> propertyStringMap) {
		VBox props = new VBox();
		//props.getChildren().add(new Label(String.valueOf(derp) + ":" + actor.getUniqueID()));
		for(String s: propertyStringMap.keySet()){
			props.getChildren().add(new Label(s + " : " + propertyStringMap.get(s)));
		}
		return props;
	}
 
}
