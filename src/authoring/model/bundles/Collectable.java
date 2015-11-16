package authoring.model.bundles;


public interface Collectable<V extends Identifiable> extends Iterable<V>{
	public int getSize();
	public void add(V value);
}
