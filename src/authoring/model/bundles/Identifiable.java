package authoring.model.bundles;

/**
 * @author Austin
 */
public interface Identifiable {
	public String getUniqueID ();
	public Identifiable getCopy ();
}
