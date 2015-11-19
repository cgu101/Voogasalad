package player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 * Contains a map of keys (as Strings) to whether or not they are being pressed (as booleans).
 *
 */
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

	/**
	 * A method to attach to a keyPressed event in order to update the map when a key is pressed.
	 * @param ke
	 */
	public void keyPressed(KeyEvent ke) {
		KeyCode code = ke.getCode();
		inputMap.put(code.toString(), true);
	}

	/**
	 * A method to attach to a keyReleased event in order to update the map when a key is released.
	 * @param ke
	 */
	public void keyReleased(KeyEvent ke) {
		KeyCode code = ke.getCode();
		inputMap.put(code.toString(), false);
	}
	/**
	 * Obtains the boolean value from a string indicating whether a key has been assigned 
	 * 
	 * @param keyName
	 * @return assignment value
	 */
	public boolean getValue (String keyName) {
		return getValue(KeyCode.valueOf(keyName));
	}
	
	/**
	 * Obtains the boolean value from a KeyCode indicating whether a key has been assigned 
	 * 
	 * @param keyCode
	 * @return assignment value
	 */
	public boolean getValue (KeyCode keyCode) {
		return inputMap.get(keyCode.toString());
	}
	
}
