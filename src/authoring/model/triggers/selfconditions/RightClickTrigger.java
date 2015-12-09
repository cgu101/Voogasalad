package authoring.model.triggers.selfconditions;

import authoring.model.actors.Actor;
import authoring.model.tree.Parameters;
import authoring.model.triggers.selftriggers.AInputSelfTrigger;
import javafx.scene.input.MouseButton;
import player.InputManager;

public class RightClickTrigger extends AInputSelfTrigger {

	private static final long serialVersionUID = -357068826674422014L;

	@SuppressWarnings("rawtypes")
	@Override
	public boolean condition(Parameters parameters, InputManager inputManager, Actor actor) {
		if(inputManager.getMouseEvent(MouseButton.SECONDARY.name())!=null){
			return true;
		}else{
			return false;
		}
	}
}
