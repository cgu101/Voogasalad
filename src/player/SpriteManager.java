package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import util.Sprite;

import authoring.model.actors.Actor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

//  Probably shouldn't be in player package... class to update, delete, keep track of sprites
public class SpriteManager {
	private Map<String, Sprite> sprites;
	private ResourceBundle myResources;
	private Map<String, Boolean> stillAlive;
	private Group group;
	
	public SpriteManager(Scene s){
		sprites = new HashMap<String, Sprite>();
		myResources = ResourceBundle.getBundle("resources/SpriteManager");
		stillAlive = new HashMap<String, Boolean>();
		BorderPane bp = (BorderPane)s.getRoot();
		group = new Group();
		bp.setCenter(group);
	}
	
	public void updateSprites(ArrayList<Actor> actors, Scene scene){
		for(Actor a : actors){
			if(sprites.containsKey(a.getUniqueID())){
				Sprite s = sprites.get(a.getUniqueID());
				s.setX((double)a.getProperties().getComponents().get("xLocation").getValue());
				s.setY((double)a.getProperties().getComponents().get("yLocation").getValue());
				System.out.println(s.getX() + ", " + s.getY() + " - " + s.getImage());
			//}else if((Boolean)a.getProperties().getComponents().get("_visible").getValue()){
			}else{
				Sprite newsp = createSprite(a);
				sprites.put(a.getUniqueID(), newsp);
				group.getChildren().add(newsp);
				newsp.play(4);
			}
			stillAlive.put(a.getUniqueID(), true);
		}
		
		// remove sprites that are no longer in actors list
		for(String id : stillAlive.keySet()){
			if(!stillAlive.get(id)){
				sprites.remove(id);
			}
		}
		stillAlive.entrySet().removeIf(e -> !e.getValue() );
		for(String id : stillAlive.keySet()){
			stillAlive.put(id,  false);
		}
		
	}
	
	public Sprite createSprite(Actor a){
		String img = (String)a.getProperties().getComponents().get("image").getValue();
		String[] dimensions = myResources.getString(img).split(",");
		return new Sprite(img,
						  Integer.parseInt(dimensions[0]),
						  Integer.parseInt(dimensions[1]));
	}
	
	public void pause(){
		for(Sprite s : sprites.values()){
			s.pause();
		}
	}
	
	public void resume(){
		for(Sprite s : sprites.values()){
			s.play();
		}
	}

}
