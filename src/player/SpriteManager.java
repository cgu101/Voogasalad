// This entire file is part of my masterpiece
// D. Collin Bachi - dcb31
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

public class SpriteManager {
	private Map<String, Sprite> sprites;
	private ResourceBundle myResources;
	private Map<String, Boolean> stillAlive;
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
				initSprite(s, a);
			}else{
				Sprite newsp = createSprite(a);
				sprites.put(a.getUniqueID(), newsp);
				myMap.getGroup().getChildren().add(newsp);
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
	 * @param s Sprite to update
	 * @param a Actor with data from which to update Sprite
	 */
	private static void initSprite(Sprite s, Actor a){
		s.setX((Double) a.getPropertyValue("xLocation") - (Double) a.getPropertyValue("width")/2);
		s.setY((Double) a.getPropertyValue("yLocation") - (Double) a.getPropertyValue("height")/2);
		s.setFitHeight(a.getPropertyValue("height"));
		s.setFitWidth(a.getPropertyValue("width"));
		if (a.getProperties().getComponents().get("angle")!=null)
			s.setRotate((double)a.getProperties().getComponents().get("angle").getValue());
	}
	

	/**
	 * Creates a visual sprite object based on a back-end actor object
	 * 
	 * @param a
	 * @return created sprite
	 */
	public static Sprite createSprite(Actor a){
		String img = (String)a.getProperties().getComponents().get("image").getValue();
		String groupID = (String)a.getProperties().getComponents().get("groupID").getValue();
		Sprite ret = createSprite(groupID, img);
		initSprite(ret, a);
		return ret;
	}
	
	/**
	 * Creates a visual sprite object based on an actor group and an image
	 * 
	 * @param group Actor group
	 * @param image Image file name
	 * @return created sprite
	 */
	public static Sprite createSprite(String group, String image){
		String[] dimensions;
		if (ResourceBundle.getBundle("resources/SpriteManager").keySet().contains(group)){
			dimensions = ResourceBundle.getBundle("resources/SpriteManager").getString(group).split(",");
		}else{
			dimensions = ResourceBundle.getBundle("resources/SpriteManager").getString(image).split(",");
		}
		Sprite ret =  new Sprite(image,
						  Integer.parseInt(dimensions[0]),
						  Integer.parseInt(dimensions[1]));
		if (dimensions[2].equals("PLAY")){
			ret.play(Integer.parseInt(dimensions[3]));
		}else if (dimensions[2].equals("PLAY_ONCE")){
			ret.playTimes(Integer.parseInt(dimensions[3]), 1);
		}else if (dimensions[2].equals("SHOW_FRAME")){
			ret.gotoFrame(Integer.parseInt(dimensions[3]),
						  Integer.parseInt(dimensions[4]));
		}
		return ret;
	}
	
	/**
	 * Pauses all sprite animations
	 */
	public void pause(){
		for(Sprite s : sprites.values()){
			if (s.getPlayFlag().equals("PLAY")) s.pause();
		}
	}
	
	/**
	 * Resumes all sprite animations 
	 */
	public void resume(){
		for(Sprite s : sprites.values()){
			if (s.getPlayFlag().equals("PLAY")) s.play();
		}
	}
	
	/**
	 * Returns the current map where Actors are placed.
	 */
	public view.map.Map getMap(){
		return myMap;
	}

}
