package data.examples;

import authoring.model.actions.IAction;
import authoring.model.actors.IActor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.IProperties;
import authoring.model.properties.Property;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import javafx.scene.paint.Color;

public class Spaceship implements IActor {
	private double myXCor, myYCor;
	private double mySize;
	private Color myColor;
	
	public Spaceship () {
		this.myXCor = 35;
		this.myYCor = 25;
		
		this.mySize = 100;
		this.myColor = Color.RED;
	}
	public Spaceship (double x, double y, double size, Color color) {
		myXCor = x;
		myYCor = y;
		mySize = size;
		myColor = color;
	}
	@Override
	public Bundle<Property<?>> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public IProperties getProperties() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public ASelfTrigger getSelfTriggers() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public IAction getActions() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	@Override
//	public void addSelfTrigger(ASelfTrigger e1) {
//		// TODO Auto-generated method stub
//		
//	}
}
