package player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {
	private Map<String,Boolean> inputMap;
	
	public InputManager () {
		this(null);
	}
	
	public InputManager (String fileName) {
		inputMap = new HashMap<String,Boolean>();
		populateMap(inputMap, fileName);
	}
	private void populateMap(Map<String,Boolean> map, String fileName) {
		try {
			ResourceBundle resources = ResourceBundle.getBundle(fileName);
			resources.keySet().forEach(k -> {map.put(KeyCode.valueOf(k).toString(), false);});
		} catch (Exception e) {
			Arrays.asList(KeyCode.values()).forEach(k -> {map.put(k.toString(), false);});
		}	
	}
	public void keyPressed(KeyEvent ke) {
		KeyCode code = ke.getCode();
		inputMap.put(code.toString(), true);
	}
	public void keyReleased(KeyEvent ke) {
		KeyCode code = ke.getCode();
		inputMap.put(code.toString(), false);
	}
	
	public boolean getValue (String keyName) {
		return getValue(KeyCode.valueOf(keyName));
	}
	public boolean getValue (KeyCode keyCode) {
		return inputMap.get(keyCode.toString());
	}
	
}
