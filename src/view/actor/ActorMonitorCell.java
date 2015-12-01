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
	private Actor actor;
	private int derp = 1;
	
	/**
	 * ActorMonitorCell Constructor
	 * This method creates a new instance of an ActorMonitorCell
	 *
	 * @param  actor The Actor that will have its properties displayed.
	 * @param  controller The PlayerController which allows the ActorMonitorCell to grab the actors' properties
	 */
	public ActorMonitorCell(PlayerController controller, Actor actor) {
		findResources();
		this.actor = actor;
		this.controller = controller;
	}

	//TODO: Make a non-default image
	private ImageView makeImage(String pic) {
		ImageView image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(pic)));
		image.setFitHeight(Double.parseDouble(myResources.getString("imagesize")));
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);
		GridPane.setRowSpan(image, 2);
		return image;
	}
	
	@Override
	protected void makeCell(String item) {
		//item is the actor "name" (Group name?)
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		List<Actor> actorList = controller.getIndividualActorsList();
		//controller.get
		for(Actor a : actorList){
			Map<String, String> propertyStringMap = controller.getPropertyStringMap(a);
			box.getChildren().add(makeImage(propertyStringMap.get("image")));
			box.getChildren().add(makePropertiesVBox(controller.getPropertyStringMap(a)));
		}
		setGraphic(box);
	} 	
	
	protected VBox makePropertiesVBox(Map<String, String> propertyStringMap) {
		VBox props = new VBox();
		props.getChildren().add(new Label(String.valueOf(derp) + ":" + actor.getUniqueID()));
		for(String s: propertyStringMap.keySet()){
			props.getChildren().add(new Label(s + " : " + propertyStringMap.get(s)));
		}
		derp++;
		return props;
	}
 
}
