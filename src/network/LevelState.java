package network;

import java.io.Serializable;
import java.util.Map;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.tree.InteractionTreeNode;


public class LevelState implements Serializable{

	private String title;
	private Map<String, Bundle<Actor>> actorMap;
	private InteractionTreeNode rootTree;
	private String backgroundImage;

	public LevelState(String title, Map<String, Bundle<Actor>> actorMap,InteractionTreeNode rootTree) {
		this.title = title;
		this.actorMap = actorMap;
		this.rootTree = rootTree;
		this.backgroundImage = null; //TODO: incorporate not-hardcoded BackgroundImage 
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getBackgroundImage(){
		return backgroundImage;
	}
	
	public Map<String,Bundle<Actor>> getActorMap(){
		return actorMap;
	}
	
	public InteractionTreeNode getRootTree(){
		return rootTree;
	}

}
