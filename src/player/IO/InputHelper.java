package player.IO;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.scene.input.KeyCode;

public class InputHelper {
	private InputMap keyHitMap;
	private InputMap keyReleaseMap;
	private InputMap keyHoldMap;
	
	protected enum KeyType {
		KEY_TYPED,
		KEY_PRESSED,
		KEY_RELEASED;
		
		public static KeyType get (String keyType) {
			return KeyType.valueOf(keyType.toUpperCase());
		}
	};
	
	private Map<KeyType, InputMap> keyBindings;
	private Set<KeyCode> heldButtons;
	
	public InputHelper () {
		keyBindings = new HashMap<KeyType, InputMap>();
		heldButtons = new HashSet<KeyCode>();
	}
	
	public void addTypedKeyControls (InputMap keyTypedMap) {
		keyHitMap = keyTypedMap;
		keyBindings.put(KeyType.KEY_TYPED, keyHitMap);
		keyBindings.put(KeyType.KEY_PRESSED, keyHitMap);
	}
	
	public void addPressedKeyControls (InputMap keyPressedMap) {
		keyHoldMap = keyPressedMap;
		keyBindings.put(KeyType.KEY_PRESSED, keyHoldMap);
	}
	
	public void addReleasedKeyControls (InputMap keyPressedMap) {
		keyReleaseMap = keyPressedMap;
		keyBindings.put(KeyType.KEY_RELEASED, keyReleaseMap);
	}
}
