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

		type = node.getIdentifier().equals(TRIGGER_IDENTIFIER) ? ResourceType.TRIGGERS.toString() : ResourceType.ACTIONS.toString();
		makePane();
		init();
	}
	protected void init() {
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
		Parameters params = node.getParameters();
		Map<String, Integer> indices = new HashMap<String,Integer>();
		for (int i = 0; i < paramList.size(); i++) {
			ParameterData parameter = paramList.get(i);
			String pType = parameter.getType();
			if (pType.equals(Double.class.getName())) {
				double val;
				try {
					val = Double.parseDouble(parameter.getValue());
				} catch (Exception ex) {
					val = 0;
					parameter.setValue(ZERO);
				}
				params.addParameter(ParametersKey.PARAM_PREFIX + Integer.toString(i), val);
			} else {
				params.addParameter(ParametersKey.PARAM_PREFIX + Integer.toString(i), parameter.getValue());
			}
		}
		node.setValue(mainOptions.getValue());
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
			if (type.equals(ResourceType.TRIGGERS.toString())) {
				controller.getAuthoringActorConstructor().getTriggerList(actors[0]).forEach(e -> {options.getItems().add(e);});
			} else {
				controller.getAuthoringActorConstructor().getActionList(actors[0]).forEach(e -> {options.getItems().add(e);});
			}
		} else {
			String[] otherActors = Arrays.copyOfRange(actors, 1, actors.length);
			if (type.equals(ResourceType.TRIGGERS.toString())) {
				controller.getAuthoringActorConstructor().getTriggerList(actors[0], otherActors).forEach(e -> {options.getItems().add(e);});
			} else {
				controller.getAuthoringActorConstructor().getActionList(actors[0],otherActors).forEach(e -> {options.getItems().add(e);});
			}
		}
		options.setOnAction(e -> {
			paramListView = makeParamList(options.getValue());
			mainPane.setCenter(paramListView);
		});
		if (options.getItems().contains(node.getValue())) {
			options.setValue(node.getValue());
		} else {
			options.setValue(options.getItems().get(0));
		}
		return options;
	}

	private ListView<ParameterData> makeParamList(String identifier) {
		int numParams = Integer.parseInt(AuthoringConfigManager.getInstance().getTypeInfo(type, identifier, ResourceType.NUM_PARAMS.toString()));
		paramList = FXCollections.observableArrayList();
		for (int i = 0; i < numParams; i++) {
			String paramPrefix = ParametersKey.PARAM_PREFIX + Integer.toString(i);
			String paramText = AuthoringConfigManager.getInstance()
					.getTypeInfo(type, identifier, paramPrefix + TEXT_SUFFIX);
			String paramType = AuthoringConfigManager.getInstance()
					.getTypeInfo(type, identifier, paramPrefix + TYPE_SUFFIX);
			String paramIndex = AuthoringConfigManager.getInstance()
					.getTypeInfo(type, identifier, paramPrefix + INDEX_SUFFIX);
			// TODO
			ParameterData data;
			if (node.getParameters().getParameter(paramPrefix) != null) {
				data = new ParameterData(paramText, paramType, paramIndex, node.getParameters().getParameter(paramPrefix).toString());
			} else {
				data = new ParameterData(paramText, paramType, paramIndex, "");
			}
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