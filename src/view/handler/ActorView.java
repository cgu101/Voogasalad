package view.handler;

import java.util.Date;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.visual.AbstractVisual;

/**
 * This class is responsible for actually manipulating the imageview of an
 * actor. It also contains up-to-date instances of each actor and its
 * appearance-related properties.
 * 
 * @author Bridget
 *
 */
public class ActorView extends AbstractVisual {
	private Actor myActor;
	private ImageView myNode;
	private double myFitWidth;
	private double dimensionRatio;
	private double myRotation;
	private double myXCoor;
	private double myYCoor;
	private boolean itsAlive;
	private String myType;
	private ActorPropertyMap myMap;
	private AuthoringController myController;

	public ActorView(Actor a, ActorPropertyMap map, String actorType, 
			double x, double y, AuthoringController ac) {
		myMap = map;
		myController = ac;
		myType = actorType;

		myActor = a;
		myXCoor = x;
		myYCoor = y;
		findResources();
		myFitWidth = Double.parseDouble(myResources.getString("defaultWidth"));
		myRotation = Double.parseDouble(myResources.getString("defaultRotation"));
		myNode = createImage();
		setupNode();
		itsAlive = true;
	}

	public ActorView(ActorView copy) {
		findResources();
		myController = copy.getController();
		myType = copy.getActorType();
		myMap = myController.getAuthoringActorConstructor().getActorPropertyMap(myType);
		
		String uniqueID = new Date().toString();
		myController.getLevelConstructor().getActorGroupsConstructor().updateActor(uniqueID, myMap);
		myActor = myController.getLevelConstructor().getActorGroupsConstructor().getActor(myType, uniqueID);

		Double offset = Double.parseDouble(myResources.getString("copyoffset"));
		myXCoor = copy.getXCoor() + offset;
		myYCoor = copy.getYCoor() + offset;
		
		myFitWidth = copy.getWidth(); 		// TODO: update properties file for size
		String img = (String) myActor.getProperties().getComponents().get("image").getValue();
		myMap.addProperty(myResources.getString("image"), img);
		myMap.addProperty(myResources.getString("x"), "" + myXCoor);
		myMap.addProperty(myResources.getString("y"), "" + myYCoor);
		
		hasChanged();
		myNode = createImage();
		setupNode();
		setRotation(copy.getRotation());
	}
	
	private String getActorType() {
		return myType;
	}
	
	private AuthoringController getController() {
		return myController;
	}

	private ImageView createImage() {
		String img = (String) myActor.getProperties().getComponents().get("image").getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(img));

		// also establish the ratio
		double width = image.getWidth();
		double height = image.getHeight();
		dimensionRatio = height / width;

		return new ImageView(image);
	}

	private void setupNode() {
		myNode.setTranslateX(myXCoor - getWidth() / 2);
		myNode.setTranslateY(myYCoor - getHeight() / 2);
		myNode.setRotate(myRotation);
		myNode.setFitWidth(myFitWidth);
		myNode.setPreserveRatio(true);
	}

	protected ImageView getImageView() {
		return myNode;
	}

	protected double getXCoor() {
		return myXCoor;
	}

	protected void setXCoor(double newX) {
		myXCoor = newX;
		myMap.addProperty(myResources.getString("x"), "" + myXCoor);
		mapChanged();
		myNode.setTranslateX(myXCoor - getWidth()/2);
	}

	protected double getYCoor() {
		return myYCoor;
	}

	protected void setYCoor(double newY) {
		myYCoor = newY;
		myMap.addProperty(myResources.getString("y"), "" + myYCoor);
		mapChanged();
		myNode.setTranslateY(myYCoor - getHeight() / 2);
	}

	protected void restoreXY(double xCoor, double yCoor) {
		setXCoor(xCoor);
		setYCoor(yCoor);
	}

	protected double getWidth() {
		return myFitWidth;
	}

	protected double getHeight() {
		return dimensionRatio * myFitWidth;
	}

	protected void scaleDimensions(double percent) {
		myFitWidth *= percent; // TODO: size?
		preserveCenter();
	}

	protected void addDimensions(double increase) {
		myFitWidth += increase;
		preserveCenter();
	}

	private void preserveCenter() {
		myNode.setFitWidth(myFitWidth);
		myNode.setPreserveRatio(true);
		restoreXY(myXCoor, myYCoor);
	}

	protected double getRotation() {
		return myRotation;
	}

	protected void setLife(boolean alive) {
		itsAlive = alive;
	}
	
	protected boolean getLife() {
		return itsAlive;
	}
	
	protected void setRotation(double rotate) {
		myRotation = rotate;
		myMap.addProperty(myResources.getString("angle"), "" + rotate);
		mapChanged();
		myNode.setRotate(rotate);
	}
	
	private void mapChanged() {
		myController.getLevelConstructor().getActorGroupsConstructor().updateActor(myActor.getUniqueID(), myMap);
	}
}
