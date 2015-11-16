package player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputManager {
	private Map<KeyCode,Boolean> inputMap;
	
	public InputManager () {
		inputMap = new HashMap<KeyCode,Boolean>();
		populateMap(inputMap);
	}
	private void populateMap(Map<KeyCode,Boolean> map) {
		Arrays.asList(KeyCode.values()).forEach(k -> {map.put(k, false);});
	}
	public void keyPressed(KeyEvent ke) {
		KeyCode code = ke.getCode();
		inputMap.put(code, true);
	}
	public void keyReleased(KeyEvent ke) {
		KeyCode code = ke.getCode();
		inputMap.put(code, false);
	}
	
	public boolean getValue (String keyName) {
		return inputMap.get(KeyCode.getKeyCode(keyName));
	}
	public boolean getValue (KeyCode keyCode) {
		return inputMap.get(keyCode);
	}
	
}
