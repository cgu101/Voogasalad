package authoring.model.actions.oneActorActions;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import engine.State;
import player.InputManager;

public class SpawnActor extends AOneActorAction {
	/**
	 * Generated serial verison ID
	 */
	private static final long serialVersionUID = 8814916109553567943L;
	private final static String ACTOR_FOLDER = ("authoring/files/actors/");
	private final static String PROPERTY_FOLDER = ("authoring/files/properties/");
	private final static String PROPERTIES = "properties";
	private final static String REGEX = ",";
	private final static String DOUBLE = "Double";
	private final static String TYPE = "type";
	private final static String X_LOCATION = "yLocation";
	private final static String Y_LOCATION = "xLocation";
	private final static String ANGLE = "angle";
	private ResourceBundle actors, properties;

	@SuppressWarnings("rawtypes")
	@Override
	public void run(InputManager inputManeger, Parameters parameters, State state, Actor actor) {
		String groupID = (String) parameters.getParameter(ParametersKey.PARAM_PREFIX + "0");
		actors = ResourceBundle.getBundle(ACTOR_FOLDER + groupID);
		List<String> propertyList = Arrays.asList(actors.getString(PROPERTIES).split(REGEX));
		Bundle<Property<?>> propertyBundle = new Bundle<Property<?>>();

		for (String propertyName : propertyList) {
			properties = ResourceBundle.getBundle(PROPERTY_FOLDER + propertyName);
			Property<?> property;
			if (properties.getString(TYPE).endsWith(DOUBLE)) {
				property = new Property<Double>(propertyName, Double.valueOf(actors.getString(propertyName)));
			} else {
				property = new Property<String>(propertyName, actors.getString(propertyName));
			}
			propertyBundle.add(property);
		}
		Timestamp currentTimeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		Actor newActor = new Actor(propertyBundle, groupID + currentTimeStamp.toString());

		if (actor.hasProperty(X_LOCATION) && actor.hasProperty(Y_LOCATION) && actor.hasProperty(ANGLE)) {
//			newActor.setProperty(X_LOCATION, (Double) actor.getPropertyValue(X_LOCATION) + 0.5*(Double) actor.getPropertyValue("width")*Math.cos(Math.toRadians( ((Double) actor.getPropertyValue(ANGLE)))));
//			newActor.setProperty(Y_LOCATION, (Double) actor.getPropertyValue(Y_LOCATION) + 0.5*(Double) actor.getPropertyValue("height")*Math.sin(Math.toRadians( ((Double) actor.getPropertyValue(ANGLE)))));
			newActor.setProperty(Y_LOCATION, actor.getPropertyValue(Y_LOCATION));
			newActor.setProperty(X_LOCATION, actor.getPropertyValue(X_LOCATION));
			newActor.setProperty(ANGLE, actor.getPropertyValue(ANGLE));
		}

		state.getActorMap().createActor(newActor);
	}
}
