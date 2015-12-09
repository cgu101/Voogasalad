package resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.KeyCode;

public final class KeyCodesMap {

	private static final Map<String, KeyCode> KEYCODES;

    static {
        Map<String, KeyCode> keycodes = new HashMap<String, KeyCode>();
        keycodes.put("F",  KeyCode.F);
        keycodes.put("F",  KeyCode.F);
        keycodes.put("F",  KeyCode.F);
        keycodes.put("F",  KeyCode.F);
        keycodes.put("F",  KeyCode.F);

        KEYCODES = Collections.unmodifiableMap(keycodes);
    }


}
