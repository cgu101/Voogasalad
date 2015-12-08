package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import util.Sprite;

import authoring.model.actors.Actor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

//  Probably shouldn't be in player package... class to update, delete, keep track of sprites
public class SpriteManager {
	private Map<String, Sprite> sprites;
	private ResourceBundle myResources;
	private Map<String, Boolean> stillAlive;
	//private Group group;
	private view.map.Map myMap;
	
	public SpriteManager(Scene s, GridPane pane){
		sprites = new HashMap<String, Sprite>();
		myResources = ResourceBundle.getBundle("resources/SpriteManager");
		stillAlive = new HashMap<String, Boolean>();
		BorderPane bp = (BorderPane)s.getRoot();
		myMap = new PlayerMap(pane);
		bp.setCenter(pane);
	}
	
	/**
	 * Core game loop functionality that iterates through all available actors, updates them, and displays
	 * @param actors
	 * @param scene
	 */
	public void updateSprites(ArrayList<Actor> actors, Scene scene){
		for(Actor a : actors){
			if(sprites.containsKey(a.getUniqueID())){
				Sprite s = sprites.get(a.getUniqueID());
				s.setX((double)a.getProperties().getComponents().get("xLocation").getValue());
				s.setY((double)a.getProperties().getComponents().get("yLocation").getValue());
			//}else if((Boolean)a.getProperties().getComponents().get("_visible").getValue()){
			}else{
				Sprite newsp = createSprite(a);
				sprites.put(a.getUniqueID(), newsp);
				myMap.getGroup().getChildren().add(newsp);
				newsp.setX((double)a.getProperties().getComponents().get("xLocation").getValue());
				newsp.setY((double)a.getProperties().getComponents().get("yLocation").getValue());
				newsp.play(0);
			}
			stillAlive.put(a.getUniqueID(), true);
		}
		
		// remove sprites that are no longer in actors list
		for(String id : stillAlive.keySet()){
			if(!stillAlive.get(id)){
				Sprite s = sprites.get(id);
				sprites.remove(id);
				myMap.getGroup().getChildren().remove(s);
			}
		}
		stillAlive.entrySet().removeIf(e -> !e.getValue() );
		for(String id : stillAlive.keySet()){
			stillAlive.put(id,  false);
		}	
	}
	
	/**
	 * Creates a visual sprite object based on a back-end actor object
	 * 
	 * @param a
	 * @return created sprite
	 */
	public Sprite createSprite(Actor a){
		String img = (String)a.getProperties().getComponents().get("image").getValue();
		String[] dimensions = myResources.getString(img).split(",");
		return new Sprite(img,
						  Integer.parseInt(dimensions[0]),
						  Integer.parseInt(dimensions[1]));
	}
	
	/**
	 * Pauses all sprite animations
	 */
	public void pause(){
		for(Sprite s : sprites.values()){
			s.pause();
		}
	}
	
	/**
	 * Resumes all sprite animations 
	 */
	public void resume(){
		for(Sprite s : sprites.values()){
			s.play();
		}
	}
	
	/**
	 * Returns the current map where Actors are placed.
	 */
	public view.map.Map getMap(){
		return myMap;
	}

}
