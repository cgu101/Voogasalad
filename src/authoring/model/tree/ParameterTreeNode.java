package authoring.model.tree;

import java.util.Arrays;
import java.util.List;

public class ParameterTreeNode extends InteractionTreeNode {
	
	protected Parameters parameters;

	public ParameterTreeNode(String value) {
		super(value);
		parameters = new Parameters();
	}
	
	public Parameters getParameters() {
		return parameters;
	}
	
	public <T> void setParameters(String parameterType, T parameter) {
		setParameters(parameterType, Arrays.asList(parameter));
	}
	
	public void setParameters(String parameterType, List<?> parameterList) {
		parameters.setParameters(parameterType, parameterList);
	}
}
