package authoring.controller.parameters;

public class ParameterData {
	private String text;
	private String type;
	private String actorIndex;
	public ParameterData(String text, String type, String actorIndex) {
		this.text = text;
		this.type = type;
		this.actorIndex = actorIndex;
	}
	public ParameterData(String[] data) {
		this(data[0], data[1], data[2]);
	}
	public String getText () {
		return text;
	}
	public String getType () {
		return type;
	}
	public String getActorIndex () {
		return actorIndex;
	}
}
