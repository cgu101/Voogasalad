package view.element;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import util.Sprite;

/**
 * I am going to refactor the mess that is MapActorManager partially into this class. Wait and behold.
 * 
 * @author Bridget
 *
 */
public class ActorHandler {
	private Actor myActor;
	private Sprite mySprite;
	private ActorPropertyMap myPropertyMap;
	
	public ActorHandler(Actor a) {
		myActor = a;
	}
	
	public ActorHandler(String s) {
		
	}
	
	public void addProperty(String key, String val) {
		myPropertyMap.addProperty(key, val);
		myActor.setProperty(key, val);
	}
	
	public Sprite getSprite() {
		return mySprite;
	}
}
