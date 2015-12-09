package view.handler;

import java.io.Serializable;

import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;

public class ActorSerializable implements Serializable {

	private static final long serialVersionUID = 1786731326124224818L;
	
	Actor a;
	ActorPropertyMap map;
	String actorType;
	double x;
	double y;
	
	ActorSerializable(Actor a, ActorPropertyMap map, String actorType, double x, double y) {
		this.a=a;
		this.map=map;
		this.actorType=actorType;
		this.x=x;
		this.y=y;		
	}
}
