package view.interactions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import authoring.controller.AuthoringController;
import authoring.controller.constructor.configreader.AuthoringConfigManager;
import authoring.controller.constructor.configreader.ResourceType;
import authoring.controller.parameters.ParameterData;
import authoring.model.tree.ActionTreeNode;
import authoring.model.tree.ParameterTreeNode;
import authoring.model.tree.Parameters;
import authoring.model.tree.ParametersKey;
import authoring.model.tree.TriggerTreeNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ParametersView {
	private static final String ZERO = "0";
	private static final String TRIGGER_IDENTIFIER = TriggerTreeNode.class.getSimpleName();
	private static final String ACTION_IDENTIFIER = ActionTreeNode.class.getSimpleName();
	private static final String PARAM_PREFIX = "param.";
	private static final String TEXT_SUFFIX = ".string";
	private static final String TYPE_SUFFIX = ".type";
	private static final String INDEX_SUFFIX = ".actorindex";

	private Stage stage;
	private Scene scene;
	private AuthoringController controller;
	private GridPane pane;
	private BorderPane mainPane;
	private ListView<ParameterData> paramListView;
	private ObservableList<ParameterData> paramList;
	private ComboBox<String> mainOptions;
	private Button finishButton;
	private ParameterTreeNode node;
	private String[] actors;
	private String type;
	private ResourceBundle myResources = ResourceBundle.getBundle("resources/ParametersView");

	public ParametersView (ParameterTreeNode node, GridPane pane, AuthoringController controller, String[] actors) {
		this.node = node;
		this.pane = pane;
		this.controller = controller;
		this.actors = actors;

		type = node.getIdentifier().equals(TRIGGER_IDENTIFIER) ? ResourceType.TRIGGERS : ResourceType.ACTIONS;
		makePane();
		init();
	}
	protected void init() {
		// TODO Auto-generated method stub
		scene = new Scene(mainPane);
		finishButton = new Button(myResources.getString("finish"));
		finishButton.setOnAction(e -> assign());
		finishButton.setFocusTraversable(false);
		mainPane.setBottom(finishButton);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.setOnCloseRequest(e -> {
//			e.consume();
			assign();
		});
		stage.initOwner(pane.getScene().getWindow());
		stage.setTitle(myResources.getString("title") + type);
		stage.showAndWait();
	}

	private void assign () {
		Parameters<Object> params = new Parameters<Object>();
		Map<String, Integer> indices = new HashMap<String,Integer>();
		paramList.forEach(e -> {
			String pType = e.getType();
			if (pType.equals(Double.class.getName())) {
				double val;
				try {
					val = Double.parseDouble(e.getValue());
				} catch (Exception ex) {
					val = 0;
					e.setValue(ZERO);
				}
				params.addParameter(ParametersKey.DOUBLE_VALUE 
						+ Integer.toString(updateMap(indices, ParametersKey.DOUBLE_VALUE)), val);
			} else if (pType.equals(String.class.getName())) {
				params.addParameter(ParametersKey.STRING_VALUE
						+ Integer.toString(updateMap(indices, ParametersKey.STRING_VALUE)), e.getValue());
			} else {
				params.addParameter(ParametersKey.PROPERTY
						+ Integer.toString(updateMap(indices, ParametersKey.PROPERTY)), e.getValue());
			}

		});
		node.setValue(mainOptions.getValue());
		node.setParameters(params);
		stage.close();
	}
	private int updateMap (Map<String, Integer> map, String key) {
		int i;
		if (!map.containsKey(key)) {
			i = 0;
		} else {
			i = map.get(key) + 1;
		}
		map.put(key, i);
		return i;
	}

	private void makePane() {
		// TODO Auto-generated method stub
		mainPane = new BorderPane();
		mainOptions = makeComboBox();
		mainPane.setTop(mainOptions);
		paramListView = makeParamList(mainOptions.getValue());
		mainPane.setCenter(paramListView);
	}

	private ComboBox<String> makeComboBox() {
		ComboBox<String> options = new ComboBox<String>();
		// assumes there will always be actors
		if (actors.length == 1) {
			if (type.equals(ResourceType.TRIGGERS)) {
				controller.getAuthoringActorConstructor().getTriggerList(actors[0]).forEach(e -> {options.getItems().add(e);});
			} else {
				controller.getAuthoringActorConstructor().getActionList(actors[0]).forEach(e -> {options.getItems().add(e);});
			}
		} else {
			String[] otherActors = Arrays.copyOfRange(actors, 1, actors.length);
			if (type.equals(ResourceType.TRIGGERS)) {
				controller.getAuthoringActorConstructor().getTriggerList(actors[0], otherActors).forEach(e -> {options.getItems().add(e);});
			} else {
				controller.getAuthoringActorConstructor().getActionList(actors[0],otherActors).forEach(e -> {options.getItems().add(e);});
			}
		}
		options.setOnAction(e -> {
			paramListView = makeParamList(options.getValue());
			mainPane.setCenter(paramListView);
		});
		options.setValue(options.getItems().get(0));
		return options;
	}

	private ListView<ParameterData> makeParamList(String identifier) {
		int numParams = Integer.parseInt(AuthoringConfigManager.getInstance().getTypeInfo(type, identifier, ResourceType.NUM_PARAMS));
		paramList = FXCollections.observableArrayList();
		for (int i = 0; i < numParams; i++) {
			String paramPrefix = PARAM_PREFIX + Integer.toString(i);
			String paramText = AuthoringConfigManager.getInstance()
					.getTypeInfo(type, identifier, paramPrefix + TEXT_SUFFIX);
			String paramType = AuthoringConfigManager.getInstance()
					.getTypeInfo(type, identifier, paramPrefix + TYPE_SUFFIX);
			String paramIndex = AuthoringConfigManager.getInstance()
					.getTypeInfo(type, identifier, paramPrefix + INDEX_SUFFIX);
			// TODO
			ParameterData data = new ParameterData(paramText, paramType, paramIndex, "");
			paramList.add(data);
		}

		ListView<ParameterData> list = new ListView<ParameterData>(paramList);
		list.setCellFactory(new Callback<ListView<ParameterData>, ListCell<ParameterData>>() {
			@Override
			public ListCell<ParameterData> call(ListView<ParameterData> list) {
				return new ParameterCell(controller, actors);
			}
		});
		list.setFocusTraversable(false);
		return list;
	}
}