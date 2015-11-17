package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import util.Sprite;

import authoring.model.actors.Actor;

//  Probably shouldn't be in player package... class to update, delete, keep track of sprites
public class SpriteManager {
	private Map<String, Sprite> sprites;
	private ResourceBundle myResources;

	
	public SpriteManager(){
		sprites = new HashMap<String, Sprite>();
		myResources = ResourceBundle.getBundle("resources/SpriteManager");
	}
	
	public void updateSprites(ArrayList<Actor> actors){
		for(Actor a : actors){
			if(sprites.containsKey(a.getUniqueID())){
				Sprite s = sprites.get(a.getUniqueID());
				s.setX((double)a.getProperties().getComponents().get("_x").getValue());
				s.setY((double)a.getProperties().getComponents().get("_y").getValue());
			}else if((Boolean)a.getProperties().getComponents().get("_visible").getValue()){
				sprites.put(a.getUniqueID(), createSprite(a));
			}
		}
	}
	
	public Sprite createSprite(Actor a){
		String img = (String)a.getProperties().getComponents().get("_image").getValue();
		String[] dimensions = myResources.getString(img).split(",");
		return new Sprite(img,
						  Integer.parseInt(dimensions[0]),
						  Integer.parseInt(dimensions[1]));
	}

}
