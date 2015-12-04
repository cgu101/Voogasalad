package authoring.model.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Parameters<V> {
	private Map<String, V> parameter_values;

	public Parameters() {
		parameter_values = new HashMap<String, V>();
	}
	
	public Parameters(Map <String, V> map) {
		parameter_values = new HashMap<String, V>(map);
	}

	public Set<Entry<String, V>> getParameterAndValues() {
		return parameter_values.entrySet();
	}

	public void addParameter(String parameter, V value) {
		parameter_values.put(parameter, value);
	}
}
