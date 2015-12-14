package authoring.events.interfaces;

import authoring.model.bundles.Identifiable;

/**
 * @author Austin
 */
public interface IAction extends Identifiable {
	public void run (String... parameters);
}
