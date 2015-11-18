package view.element;

import java.util.ArrayList;
import java.util.Map.Entry;

import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.properties.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import player.PlayerController;
import view.actor.ActorCell;
import view.element.AbstractDockElement;
import view.screen.AbstractScreenInterface;

public class ActorMonitor extends AbstractDockElement {

	private ListView<String> actorListView;
	private ObservableList<String> actors;
	private PlayerController controller; 
	private ImageView image;
	
	public ActorMonitor(GridPane pane, GridPane home, String title, AbstractScreenInterface screen, PlayerController controller) {
		super(pane, home, title, screen);
		findResources();
		this.controller = controller;
		makePane();
	}

	@Override
	protected void makePane() {
		addLabelPane();
		actors = FXCollections.observableArrayList(new ArrayList<String>());
		actors.addAll(controller.getActorMap().keySet());
		actorListView.prefHeightProperty().bind(screen.getScene().heightProperty());
		actorListView.setFocusTraversable(false);

		int i = 0;
		for(Actor a : controller.getActorList()){
			pane.add(makeImage(a), i, 1);
			//pane.add(makeProperties(a), i+1, 1);
		}
		actorListView = new ListView<String>(actors);
		pane.add(actorListView, 0, 1);
		pane.setAlignment(Pos.TOP_CENTER);
		pane.setMaxWidth(Double.parseDouble(myResources.getString("width")));
	}
	
	private Node makeProperties(Actor a) {
//		for(Property<?> b : a.getProperties()){
//			b.
//		}
		return null;
	}

	//TODO: Make a non-default image
	private ImageView makeImage(Actor a) {
		image = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("rcd.jpg")));
		image.setFitHeight(Double.parseDouble(myResources.getString("imagesize")));
		image.setPreserveRatio(true);
		image.setSmooth(true);
		image.setCache(true);
		GridPane.setRowSpan(image, 2);
		return image;
	}

	private void addLabelPane() {
		GridPane labelPane = makeLabelPane();
		pane.add(labelPane, 0, 0);
		GridPane.setColumnSpan(labelPane, 2);
	}

}
