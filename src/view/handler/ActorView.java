package view.handler;

import java.util.Date;

import authoring.controller.AuthoringController;
import authoring.model.actors.Actor;
import authoring.model.actors.ActorPropertyMap;
import javafx.scene.image.Image;
import player.SpriteManager;
import util.Sprite;
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
	private Sprite mySprite;
	private double myFitWidth;
	private double dimensionRatio;
	private double myRotation;
	private double myXCoor;
	private double myYCoor;
	private String myType;
	private ActorPropertyMap myMap;
	private AuthoringController myController;

	public ActorView(Actor a, ActorPropertyMap map, String actorType, double x, double y, AuthoringController ac) {
		myMap = map;
		myController = ac;
		myType = actorType;

		myActor = a;
		myXCoor = x;
		myYCoor = y;
		findResources();
		myFitWidth = a.getPropertyValue(myResources.getString("width"));
		myRotation = a.getPropertyValue(myResources.getString("angle"));
		mySprite = createImage();
		setupNode();
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
		myFitWidth = copy.getWidth(); 
		String img = (String) myActor.getProperties().getComponents().get("image").getValue();
		
		myMap.addProperty(myResources.getString("width"), "" + getWidth());
		myMap.addProperty(myResources.getString("image"), img);
		myMap.addProperty(myResources.getString("x"), "" + getXCoor());
		myMap.addProperty(myResources.getString("y"), "" + getYCoor());

		mySprite = createImage();
		setRotation(copy.getRotation());
		setupNode();
		myMap.addProperty(myResources.getString("height"), "" + getHeight());
		mapChanged();
	}

	protected Actor getActor() {
		return myActor;
	}
	
	private String getActorType() {
		return myType;
	}

	private AuthoringController getController() {
		return myController;
	}

	private Sprite createImage() {
		String img = (String) myActor.getProperties().getComponents().get("image").getValue();
		/*Image image = new Image(getClass().getClassLoader().getResourceAsStream(img));

		// also establish the ratio
		double width = image.getWidth();
		double height = image.getHeight();
		dimensionRatio = height / width;
		 */
		// return new ImageView(image);
		Sprite ret = SpriteManager.createSprite(myActor.getGroupName(), img);
		double width = ret.getImage().getWidth();
		double height = ret.getImage().getHeight();
		System.out.println("rhea rhea rhea " + width);
		//myXCoor -= 250;
		System.out.println("no nanas " + height);
		System.out.println("woowoo " + width);
		dimensionRatio = height / width;
		return ret;
	}

	private void setupNode() {
		mySprite.setTranslateX(myXCoor - getWidth() / 2);
		mySprite.setTranslateY(myYCoor - getHeight() / 2);
		mySprite.setRotate(myRotation);
		mySprite.setFitWidth(myFitWidth);
		mySprite.setPreserveRatio(true);
	}

	protected Sprite getSprite() {
		return mySprite;
	}

	protected double getXCoor() {
		return myXCoor;
	}

	protected void setXCoor(double newX) {
		myXCoor = newX;
		myMap.addProperty(myResources.getString("x"), "" + myXCoor);
		mapChanged();
		mySprite.setTranslateX(myXCoor - getWidth() / 2);
	}

	protected double getYCoor() {
		return myYCoor;
	}

	protected void setYCoor(double newY) {
		myYCoor = newY;
		myMap.addProperty(myResources.getString("y"), "" + myYCoor);
		mapChanged();
		mySprite.setTranslateY(myYCoor - getHeight() / 2);
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
		myMap.addProperty(myResources.getString("width"), "" + myFitWidth);
		myMap.addProperty(myResources.getString("height"), "" + myFitWidth*dimensionRatio);
		mapChanged();
		preserveCenter();
	}

	protected void addDimensions(double increase) {
		myFitWidth += increase;
		myMap.addProperty(myResources.getString("width"), "" + myFitWidth);
		myMap.addProperty(myResources.getString("height"), "" + myFitWidth*dimensionRatio);
		mapChanged();
		preserveCenter();
	}

	private void preserveCenter() {
		mySprite.setFitWidth(myFitWidth);
		mySprite.setPreserveRatio(true);
		restoreXY(myXCoor, myYCoor);
	}

	protected double getRotation() {
		return myRotation;
	}

	protected void setRotation(double rotate) {
		myRotation = rotate;
		myMap.addProperty(myResources.getString("angle"), "" + rotate);
		mapChanged();
		mySprite.setRotate(rotate);
	}

	private void mapChanged() {
		myController.getLevelConstructor().getActorGroupsConstructor().updateActor(myActor.getUniqueID(), myMap);
	}
}
