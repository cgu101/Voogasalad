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
	private Map<String, Boolean> stillAlive;
	
	public SpriteManager(){
		sprites = new HashMap<String, Sprite>();
		myResources = ResourceBundle.getBundle("resources/SpriteManager");
		stillAlive = new HashMap<String, Boolean>();
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
		String img = (String)a.getProperties().getComponents().get("_image").getValue();
		String[] dimensions = myResources.getString(img).split(",");
		return new Sprite(img,
						  Integer.parseInt(dimensions[0]),
						  Integer.parseInt(dimensions[1]));
	}

}
