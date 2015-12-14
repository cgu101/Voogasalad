// This entire file is part of my masterpiece.
// Connor Usry (cgu4)

package view.actor;

import java.util.Map;
import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import player.SpriteManager;
import player.controller.PlayerController;
import player.controller.PlayerStateUtility;
import util.Sprite;

public class ActorMonitorCell extends AbstractListCell<String> {

	private PlayerStateUtility playerStateUtility;
	private final ResourceBundle spriteResources = ResourceBundle.getBundle("resources/SpriteManager");
	
	/**
	 * ActorMonitorCell Constructor
	 * This method creates a new instance of an ActorMonitorCell
	 *
	 * @param  actor The Actor that will have its properties displayed.
	 * @param  controller The PlayerController which allows the ActorMonitorCell to grab the actors' properties
	 */
	public ActorMonitorCell(PlayerStateUtility playerStateUtility) {
		findResources();
		this.playerStateUtility = playerStateUtility;
	}

	//TODO: Make a non-default image
	private VBox makeImage(String pic, String name) {
		VBox v = new VBox();
		//Sprite sprite = createSpriteFromImg(pic);
		Sprite sprite = SpriteManager.createSprite(playerStateUtility.getActorFromString(name));
		sprite.setFitHeight(Double.parseDouble(myResources.getString("imagesize")));
		sprite.setPreserveRatio(true);
		sprite.setSmooth(true);
		sprite.setCache(true);
		
		GridPane.setRowSpan(sprite, 1);
		Label label = new Label(name);
		label.setTextFill(Color.web("#0076a3"));
		v.getChildren().addAll(label, sprite);
		return v;
	}
	
	@Override
	protected void makeCell(String item) {
		HBox box = new HBox(5);
		box.setAlignment(Pos.CENTER_LEFT);
		
		Map<String, String> propertyStringMap = playerStateUtility.getPropertyStringMapFromActorString(item);
		box.getChildren().add(makeImage(propertyStringMap.get("image"), item));
		box.getChildren().add(makePropertiesVBox(propertyStringMap));
		
		setGraphic(box);
	} 	
	
	protected VBox makePropertiesVBox(Map<String, String> propertyStringMap) {
		VBox props = new VBox();
		for(String s: propertyStringMap.keySet()){
			Label l = new Label(s + " : " + propertyStringMap.get(s));
			l.setAlignment(Pos.CENTER_LEFT);
			props.getChildren().add(l);
		}
		props.setAlignment(Pos.CENTER_LEFT);
		return props;
	}
	
	public Sprite createSpriteFromImg(String img){
		String[] dimensions = spriteResources.getString(img).split(",");
		return new Sprite(img,
				  Integer.parseInt(dimensions[0]),
				  Integer.parseInt(dimensions[1]));
		
		
	}
 
}
