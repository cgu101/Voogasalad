package engine.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class GroovyTest {
	public static void main (String[] args) throws Exception {
		
        // setup groovy and make it aware of Java objects
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("groovy");
        Actor actor0 = new Actor(0);
        Actor actor1 = new Actor(1);
        engine.put("arg0", actor0);
        engine.put("arg1", actor1);

        // evaluate in groovy
        System.out.println("During");
        try {
        	engine.eval("arg0.setValue(arg0.getValue() + 1)");
        } catch(ScriptException ex) {
            System.out.println(ex);
        }
        System.out.println(actor0.value);
        System.out.println(actor1.value);
    }
}

class Actor {
	protected int value;
	public Actor () {
		this(0);
	}
	public Actor (int value) {
		setValue(value);
	}
	public int getValue() {
		return value;
	}
	protected void setValue(int value) {
		this.value = value;
	}
}