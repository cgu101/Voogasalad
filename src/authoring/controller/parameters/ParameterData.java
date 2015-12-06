package authoring.controller.parameters;

public class ParameterData {
	private String text;
	private String type;
	private String actorIndex;
	private String value;
	public ParameterData(String text, String type, String actorIndex, String value) {
		this.text = text;
		this.type = type;
		this.actorIndex = actorIndex;
		this.value = value;
	}
	public ParameterData(String[] data) {
		this(data[0], data[1], data[2], null);
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
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
