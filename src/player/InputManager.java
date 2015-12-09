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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Contains a map of keys (as Strings) to whether or not they are being pressed
 * (as booleans).
 *
 */
public class InputManager {
	private Map<String, Boolean> inputMap;
	private Map<String, MouseEvent> keyMap;
	private InputTree inputTree;
	private Set<String> keys;
	private final String KeyEventsResource = "resources/gameplayer/Inputs";

	public InputManager() {
		this(null);
	}

	public InputManager(String fileName) {
		keyMap = new HashMap<String, MouseEvent>();
		inputMap = new HashMap<String, Boolean>();
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
		populateMap(inputMap, fileName);
		ResourceBundle keyEventResource = ResourceBundle.getBundle(KeyEventsResource);
		for(String key : keyEventResource.keySet()){
			keyMap.put(key, null);
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

	public void mousePressed(MouseEvent me) {
		MouseButton code = me.getButton();
		if (keyMap.containsKey(code.name())) {
			keyMap.put(code.name(), me);
		}
	}
	
	public void mouseReleased(MouseEvent me) {
		MouseButton code = me.getButton();
		if (keyMap.containsKey(code.name())) {
			keyMap.put(code.name(), null);
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
