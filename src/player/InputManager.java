package player;

import java.util.Arrays;
import java.util.EnumMap;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Manages game inputs through two maps of key codes and mouse buttons,
 * respectively, to booleans representing whether they are being pressed (true)
 * or not (false)
 * 
 * @author Sung
 * @author Tyler
 */
public class InputManager {
	private EnumMap<KeyCode, Boolean> keyMap;
	private EnumMap<MouseButton, Boolean> mouseMap;

	/**
	 * Initializes the key and mouse maps and calls for them to be populated.
	 */
	public InputManager() {
		keyMap = new EnumMap<KeyCode, Boolean>(KeyCode.class);
		mouseMap = new EnumMap<MouseButton, Boolean>(MouseButton.class);
		populateMaps();
	}

	/**
	 * Initially populates the key and mouse maps with all possible key codes
	 * and mouse buttons, respectively, setting each entry's value to false.
	 */
	private void populateMaps() {
		Arrays.asList(KeyCode.values()).forEach(k -> {
			keyMap.put(k, false);
		});
		Arrays.asList(MouseButton.values()).forEach(k -> {
			mouseMap.put(k, false);
		});
	}

	/**
	 * Obtains the boolean value to which the key code or mouse button is
	 * mapped, indicating whether the key or mouse button is currently being
	 * pressed.
	 * 
	 * @param code
	 *            the key code or mouse button
	 * @return if {@code code} is contained in either the key or mouse map, the
	 *         current boolean value: true if being pressed, false if not.
	 *         otherwise, false
	 */
	public <K extends Enum<K>> boolean getValue(K code) {
		return (keyMap.containsKey(code) && keyMap.get(code)) || (mouseMap.containsKey(code) && mouseMap.get(code));
	}

	/**
	 * Sets the boolean value to which the key code or mouse button is mapped in
	 * the appropriate map.
	 * 
	 * @param code
	 *            the key code or mouse button
	 * @param value
	 *            the value to be set: true if pressed, false if released
	 * @param inputMap
	 *            the key map or mouse map
	 */
	private <K extends Enum<K>> void setValue(K code, Boolean value, EnumMap<K, Boolean> inputMap) {
		if (inputMap.containsKey(code)) {
			inputMap.put(code, value);
		}
	}

	/**
	 * A method to attach to a key pressed event in order to update the key map
	 * when a key is pressed.
	 * 
	 * @param ke
	 *            the key event
	 */
	public void keyPressed(KeyEvent ke) {
		setValue(ke.getCode(), true, keyMap);
	}

	/**
	 * A method to attach to a key released event in order to update the key map
	 * when a key is released.
	 * 
	 * @param ke
	 *            the key event
	 */
	public void keyReleased(KeyEvent ke) {
		setValue(ke.getCode(), false, keyMap);
	}

	/**
	 * A method to attach to a mouse pressed event in order to update the mouse
	 * map when a mouse button is pressed.
	 * 
	 * @param me
	 *            the mouse event
	 */
	public void mousePressed(MouseEvent me) {
		setValue(me.getButton(), true, mouseMap);
	}

	/**
	 * A method to attach to a mouse released event in order to update the mouse
	 * map when a mouse button is released.
	 * 
	 * @param me
	 *            the mouse event
	 */
	public void mouseReleased(MouseEvent me) {
		setValue(me.getButton(), false, mouseMap);
	}
}
