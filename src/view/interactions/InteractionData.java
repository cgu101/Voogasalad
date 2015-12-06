package view.interactions;

import java.util.List;
import authoring.controller.parameters.ParameterData;

public class InteractionData {
	private String type;
	private String classID;
	private String[] actors;
	private List<ParameterData> currentValues;
	
	public InteractionData (String type,String classID, List<ParameterData> values, String[] actors){
		this.type = type;
		this.classID = classID;
		this.currentValues = values;
		this.actors = actors;
	}
	public List<ParameterData> getValues () {
		return currentValues;
	}
	protected String[] getActors() {
		return actors;
	}
	public String getID() {
		return classID;
	}
	public String getType() {
		return type;
	}
}
