package authoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import authoring.model.actions.IAction;
import authoring.model.actions.MoveAction;
import authoring.model.actors.Actor;
import authoring.model.actors.Asteroid;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import authoring.model.triggers.ATriggerEvent;
import authoring.model.triggers.selftriggers.ASelfTrigger;
import authoring.model.triggers.selftriggers.TrueTrigger;
import exceptions.data.GameFileException;

public class GameAuthoringBackend {

	public static void main (String args[]) throws GameFileException{

		Asteroid ast = new Asteroid();

		IAction move = new MoveAction();
		Bundle<IAction> actionBundle = new Bundle<IAction>();
		actionBundle.add(move);
		
		ASelfTrigger moveTrigger = new TrueTrigger(actionBundle, new ArrayList<Actor>(){{add(ast);}});
		
		moveTrigger.condition();
		moveTrigger.condition();
		
	}
	
	public void createGraphLink (Actor a, List<Actor> b, ATriggerEvent e, List<IAction> act, List<Property> prop, List<Objects> vals){
//		
	}

}
