package authoring.model.tree;

public class ParameterTreeNode extends InteractionTreeNode {
	
	protected Parameters parameters;

	public ParameterTreeNode(String value) {
		super(value);
		parameters = new Parameters();
	}
	
	public Parameters getParameters() {
		return parameters;
	}
//	
//	public <T> void setParameters(String parameter, T value) {
//		parameters.setParameters(parameter, value);
//	}
}
