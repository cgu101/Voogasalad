package authoring.model.tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parameters {
	private Map<String, List<?>> map;

	public Parameters() {
		map = new HashMap<String, List<?>>();
	}

	public List<?> getParameterList(String key) {
		return map.get(key);
	}

	public void setParameters(String parameterType, List<?> parameterList) {
		map.put(parameterType, parameterList);
	}

	public <T> void setParameters(String parameterType, T parameter) {
		setParameters(parameterType, Arrays.asList(parameter));
	}
}
