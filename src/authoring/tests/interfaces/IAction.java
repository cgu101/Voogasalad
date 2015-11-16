package authoring.tests.interfaces;

import authoring.model.bundles.Identifiable;

public interface IAction extends Identifiable {
	public void run (String... parameters);
}
