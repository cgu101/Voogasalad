package player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Contains a map of keys (as Strings) to whether or not they are being pressed
 * (as booleans).
 *
 */
public class InputManager {
	private Map<String, Boolean> inputMap;
	private InputTree inputTree;
	private Set<String> keys;

	public InputManager() {
		this(null);
	}

	public InputManager(String fileName) {
		inputMap = new HashMap<String, Boolean>();
		populateMap(inputMap, fileName);
		keys = new HashSet<String>();
		Scanner s;
		try {
			s = new Scanner(new File("src/KeyLibrary/keys.txt"));
			while (s.hasNext()) {
				String next = s.nextLine();
				try {
					keys.add(next);
				} catch (java.lang.NullPointerException e) {
					e.printStackTrace();
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void populateMap(Map<String, Boolean> map, String fileName) {
		try {
			ResourceBundle resources = ResourceBundle.getBundle(fileName);
			resources.keySet().forEach(k -> {
				map.put(k, false);
			});
		} catch (Exception e) {
			try {
				for (String key : keys) {
					map.put(key, false);
				}
			} catch (Exception ee) {
				Arrays.asList(KeyCode.values()).forEach(k -> {
					map.put(k.getName(), false);
				});
			}
		}
	}

	/**
	 * A method to attach to a keyPressed event in order to update the map when
	 * a key is pressed.
	 * 
	 * @param ke
	 */
	public void keyPressed(KeyEvent ke) {
		KeyCode code = ke.getCode();
		if (inputMap.containsKey(code.getName())) {
			inputMap.put(code.getName(), true);
		}
	}

	/**
	 * A method to attach to a keyReleased event in order to update the map when
	 * a key is released.
	 * 
	 * @param ke
	 */
	public void keyReleased(KeyEvent ke) {
		KeyCode code = ke.getCode();
		if (inputMap.containsKey(code.getName())) {
			inputMap.put(code.getName(), false);
		}
	}

	/**
	 * Obtains the boolean value from a string indicating whether a key has been
	 * assigned
	 * 
	 * @param keyName
	 * @return assignment value
	 */
	public boolean getValue(String keyName) {
		return getValue(KeyCode.getKeyCode(keyName));
	}

	/**
	 * Obtains the boolean value from a KeyCode indicating whether a key has
	 * been assigned
	 * 
	 * @param keyCode
	 * @return assignment value
	 */
	public boolean getValue(KeyCode keyCode) {
		if (inputMap.containsKey(keyCode.getName())) {
			return inputMap.get(keyCode.getName());
		}
		return false;
	}

	// TODO
	public void setInputTree(InputTree tree) {
		inputTree = tree;
	}

	public boolean checkSequence(String key) {
		return inputTree.checkSequenceKey(key);
	}

}
