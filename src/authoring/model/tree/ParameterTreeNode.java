package authoring.model.tree;

public class ParameterTreeNode extends InteractionTreeNode {

	private static final long serialVersionUID = -889435400554146354L;
	protected Parameters parameters;

	public ParameterTreeNode(String value) {
		super(value);
		parameters = new Parameters();
	}
	
	public Parameters getParameters() {
		return parameters;
	}
	
	public <T> void setParameters(Parameters p) {
		parameters = p;
	}
	
	public <T> void setParameters(String parameter, T value) {
		parameters.addParameter(parameter, value);
	}
	
//	@Override
//	public String getValue () {
//		return String.format("%s.%s", super.getValue(), parameters.toString());
//	}
}
