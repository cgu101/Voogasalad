package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import authoring.model.InteractionTreeNode;
import authoring.model.actors.Actor;

// runs the interaction tree
public class InteractionExecutor {
	private InteractionTreeNode root;

	public InteractionExecutor (InteractionTreeNode root) {
		this.root = root;
	}

	// actors will be found in a map of lists (maybe)
	public void run (Map<String, List<Actor>> actorMap) {
		//	for each actor:
		//	traverse tree, find pairs of actors

		//  get trigger script from file
		//		
		//  if trigger conditions true:
		
		//	should retrieve keys for the scripts from the tree
		List<String> actions = new ArrayList<String>();
		for (String action : actions) {
			// get scripts
			// run scripts
		}
	}

	// runs scripts
	private boolean applyGroovy (String script) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("groovy");
		// see example/GroovyTest for example
		
		return false;
	}

}
