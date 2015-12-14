package authoring.model.actions.oneActorActions;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import authoring.files.properties.ActorProperties;
import authoring.model.actions.AOneActorAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import engine.State;
import javafx.scene.input.MouseButton;
import player.InputManager;

public class SpawnTowardsClick extends AOneActorAction{

	private static final long serialVersionUID = 7562603379850379252L;
	private final static String ACTOR_FOLDER = ("authoring/files/actors/");
	private final static String PROPERTY_FOLDER = ("authoring/files/properties/");
	private final static String PROPERTIES = "properties";
	private final static String REGEX = ",";
	private final static String DOUBLE = "Double";
	private final static String TYPE = "type";
	private final static String X_LOCATION = ActorProperties.X_LOCATION.getKey();
	private final static String Y_LOCATION = ActorProperties.Y_LOCATION.getKey();
	private final static String ANGLE = ActorProperties.ANGLE.getKey();
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
			Double xA = actor.getPropertyValue(ActorProperties.X_LOCATION.getKey());
			Double yA = actor.getPropertyValue(ActorProperties.Y_LOCATION.getKey());
			Double xB = inputManeger.getMouseEvent(MouseButton.PRIMARY.name()).getX();
			Double yB = inputManeger.getMouseEvent(MouseButton.PRIMARY.name()).getY();
			if (Double.compare(xA, xB) == 0 && Double.compare(yA, yB) == 0)
				return;
			Double slope = (yB - yA) / (xB - xA);
			Double angle = Math.toDegrees(Math.atan(slope));
			if (xA > xB)
				angle += 180;
			newActor.setProperty(X_LOCATION, xA);
			newActor.setProperty(Y_LOCATION, yA);
			newActor.setProperty(ANGLE, angle);
		}

		state.getActorMap().createActor(newActor);
	}

}
