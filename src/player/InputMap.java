package player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.tests.interfaces.IReaction;
import javafx.scene.input.KeyCode;

public abstract class InputMap {
	private Map<KeyCode, IReaction> keyMap;
	
	public InputMap () {
		keyMap = new HashMap<KeyCode, IReaction>();
	}
	
	public boolean addKeyBinding (KeyCode button, IReaction action) {
		keyMap.put(button, action);
		
		if (keyMap.containsKey(button)) {
			return false;
		} else {
			return true;
		}
	}
	
	public void react (List<KeyCode> buttons) {
		for (KeyCode button : buttons) { react(button); }
	}
	
	public abstract void react (KeyCode button);
}
