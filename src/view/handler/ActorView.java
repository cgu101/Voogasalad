package view.handler;

import authoring.model.actors.Actor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import view.visual.AbstractVisual;

/**
 * This class is responsible for actually manipulating the imageview of an actor. It also contains up-to-date
 * instances of each actor and its appearance-related properties.
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
	
	public ActorView(Actor a, double x, double y) {
		myActor = a;
		myXCoor = x;
		myYCoor = y;
		findResources();
		myFitWidth = Double.parseDouble(myResources.getString("defaultWidth"));
		myRotation = Double.parseDouble(myResources.getString("defaultRotation"));
		myNode = createImage();
		setupNode();
	}
	
	public ActorView(ActorView copy) {
		this.myActor = new Actor(copy.getActor());
		findResources();
		myFitWidth = Double.parseDouble(myResources.getString("defaultWidth"));
		myRotation = Double.parseDouble(myResources.getString("defaultRotation"));
		myNode = createImage();
		
		myXCoor = getWidth()/2;
		myYCoor = getHeight()/2;
		
		setupNode();
	}
	
	private ImageView createImage() {
		String img = (String) myActor.getProperties().getComponents().get("image").getValue();
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(img));
		
		// also establish the ratio
		double width = image.getWidth();
		double height = image.getHeight();
		dimensionRatio = height/width;		
		
		return new ImageView(image);
	}
	
	private void setupNode() {
		myNode.setTranslateX(myXCoor - getWidth()/2);
		myNode.setTranslateY(myYCoor - getHeight()/2);
		myNode.setRotate(myRotation);
		myNode.setFitWidth(myFitWidth);
		myNode.setPreserveRatio(true);
	}
	
	private Actor getActor() {
		return myActor;
	}
	
	protected ImageView getImageView() {
		return myNode;
	}
	
	protected double getXCoor() {
		return myXCoor;
	}
	
	protected void setXCoor(double newX) {
		myXCoor = newX;
		myNode.setTranslateX(myXCoor - getWidth()/2);
	}
	
	protected double getYCoor() {
		return myYCoor;
	}
	
	protected void setYCoor(double newY) {
		myYCoor = newY;
		myNode.setTranslateY(myYCoor - getHeight()/2); 
	}
	
	// call when we have the coordinates for the top left corner of the node, not the center
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
		myFitWidth *= percent;
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
	
	protected void setRotation(double rotate) {
		myRotation = rotate;
		myNode.setRotate(rotate);
	}
}
