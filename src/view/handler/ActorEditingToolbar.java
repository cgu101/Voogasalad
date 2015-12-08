package view.handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import view.map.Map;
import view.map.MapViewManager;
import view.visual.AbstractVisual;

public class ActorEditingToolbar extends AbstractVisual {
	private ToolBar myToolbar;
	private Label defaultLabel;
	private Map map;
	private boolean rectangleOn;
	private MapViewManager meep;
//	private Function<Node, Node> exitKey;

	public ActorEditingToolbar(ToolBar tb, Map map, MapViewManager mvm) {
		findResources();
		myToolbar = tb;
		this.map = map;
		defaultLabel = makeLabel(myResources.getString("defaultPrompt"));
		restoreToolbar();
		meep = mvm;
		rectangleOn = false;
	}
	
	protected void makeMoveActorToolbar(ActorView a, Rectangle r, EventHandler<ActionEvent> restoreHandler) {
		Button undo = makeButton(myResources.getString("restore"), restoreHandler);
		Pane spacer = makeSpacer();
		Button finish = makeFinishButton(r);
		replaceToolbar(makeLabel(myResources.getString("moveInstru")), spacer, undo, finish);
	}
		
	protected void makeRotateActorToolbar(ActorView a, double heading, Rectangle r,
			EventHandler<ActionEvent> rotateRight, EventHandler<ActionEvent> rotateLeft) {
		Button enterVal = makeButton(myResources.getString("actual"), e -> rotateDialog(a, true));
		Button enterVal2 = makeButton(myResources.getString("relative"), e -> rotateDialog(a, false));
		Separator s = new Separator();
		Button left = makeButton(makeImage(myResources.getString("left")), rotateLeft);
		Button right = makeButton(makeImage(myResources.getString("right")), rotateRight);
		Button reset = makeButton(myResources.getString("restore"), e -> a.setRotation(heading));
		Button finish = makeFinishButton(r);
		Pane spacer = makeSpacer();
		replaceToolbar(makeLabel(myResources.getString("rotateInstru")), enterVal, enterVal2, s, left, right, spacer,
				reset, finish);
	}
	
	private void rotateDialog(ActorView a, boolean absolute) {
		double initialHeading = 0;

		if (!absolute) {
			initialHeading = a.getRotation();
		}

		TextInputDialog popup = new TextInputDialog();
		popup.setTitle(myResources.getString("rotate"));
		popup.setHeaderText(myResources.getString("rotateInput"));
		popup.showAndWait();

		String newVal = popup.getEditor().getText();
		try {
			double degrees = Double.parseDouble(newVal);
			a.setRotation(degrees + initialHeading);
		} catch (Exception e) {
			Alert error = new Alert(AlertType.ERROR, myResources.getString("parsedoubleerror"), ButtonType.OK);
			error.showAndWait();
		}
	}
	
	protected void makeResizeActorToolbar(ActorView a, Rectangle r, 
			EventHandler<ActionEvent> growActorHandler, EventHandler<ActionEvent> decreaseActorHandler) {
		Button plus = makeButton(makeImage(myResources.getString("plus")), growActorHandler);
		Button minus = makeButton(makeImage(myResources.getString("minus")), decreaseActorHandler);
		Pane spacer = makeSpacer();
		Button finish = makeFinishButton(r);

		replaceToolbar(makeLabel(myResources.getString("resizeInstru")), plus, minus, spacer, finish);
	}
			
	private Label makeLabel(String desc) {
		Label l = new Label(desc);
		l.setFont(textFont);
		return l;
	}
	
	private Button makeButton(String title, EventHandler<ActionEvent> handler) {
		Button b = new Button(title);
		b.setOnAction(handler);
		b.setFont(textFont);
		return b;
	}
	
	private Button makeButton(ImageView image, EventHandler<ActionEvent> handler) {
		Button b = new Button();
		b.setOnAction(handler);
		b.setGraphic(image);
		return b;
	}

	private Button makeFinishButton(Node... elementsToRemove) {
		return makeButton(myResources.getString("finish"), e -> {
			meep.removeElements(elementsToRemove);
			restoreToolbar();
			map.setPanEnabled(true);
			setEditing(false);;
		});
	}
	
	private ImageView makeImage(String s) {
		Image img = new Image(getClass().getClassLoader().getResourceAsStream(s));
		ImageView image = new ImageView(img);
		image.setFitHeight(Double.parseDouble(myResources.getString("height")));
		image.setPreserveRatio(true);
		return image;
	}
	
	private Pane makeSpacer() {
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		return spacer;
	}

	private void replaceToolbar(Node... nodes) {
		myToolbar.setPrefWidth(map.getMapWidth());
		myToolbar.getItems().clear();
		for (Node n : nodes) {
			myToolbar.getItems().add(n);
		}
	}

	protected void restoreToolbar() {
		myToolbar.getItems().clear();
		myToolbar.getItems().add(defaultLabel);
	}
	
	protected void setEditing(boolean on) {
		rectangleOn = on;
	}
	
	protected boolean isEditing() {
		return rectangleOn;
	}
}
