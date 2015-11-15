package authoring.model.triggers;

import java.util.List;
import java.util.Map;

import authoring.model.actions.IAction;
import authoring.model.actors.Actor;
import authoring.model.bundles.Bundle;
import authoring.model.bundles.Identifiable;

public interface ITriggerEvent extends Identifiable {

	public abstract boolean condition(List<IAction> actions, Map<String, Bundle<Actor>> map, Actor... actors);

}
