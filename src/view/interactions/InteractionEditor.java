package view.interactions;

import java.util.List;

import authoring.controller.parameters.ParameterData;
import javafx.scene.control.ListView;

public class InteractionEditor {
	private String typeID;
	private String parameters;
	private ListView<ParameterCell> paramList;
	
	public InteractionEditor () {
		
	}
	
	protected List<ParameterData> getParameterData() {
		// TODO
		return null;
	}
}

