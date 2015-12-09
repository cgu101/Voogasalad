package authoring.model.actors;

import java.io.Serializable;
import java.util.Observable;

import authoring.model.bundles.Bundle;
import authoring.model.bundles.Identifiable;
import authoring.model.game.ActorDependencyInjector;
import authoring.model.properties.Property;

public class Actor extends Observable implements Identifiable, IActor, Serializable {
	/**
	 * Generated serial version iD
	 */
	private static final long serialVersionUID = 9139664644586189227L;

	private Bundle<Property<?>> myPropertyBundle;
	private String identifier;
	private ActorType actorType;

	public Actor(Bundle<Property<?>> myPropertyBundle, String identifier) {
		this.myPropertyBundle = myPropertyBundle;
		this.identifier = identifier;
		setupType(identifier);
	}

	public Actor(Actor a) {
		this.myPropertyBundle = new Bundle<Property<?>>(a.getProperties());
		this.identifier = a.getUniqueID();
		setupType(identifier);
	}

	private void setupType(String id) {
		if (id.startsWith(ActorType.GLOBAL.toString())) {
			this.actorType = ActorType.GLOBAL;
		} else {
			this.actorType = ActorType.LOCAL;
		}
	}

	public ActorType getActorType() {
		return actorType;
	}

	public void updateObservers(ActionMail mail) {
		setChanged();
		notifyObservers(mail);
	}

	@Override
	public Bundle<Property<?>> getProperties() {
		return myPropertyBundle;
	}

	@Override
	public String getUniqueID() {
		return identifier;
	}

	@Override
	public Identifiable getCopy() {
		return new Actor(this);
	}

	@Override
	public Property<?> getProperty(String identifier) {
		return (Property<?>) myPropertyBundle.getComponents().get(identifier);
	}

	@SuppressWarnings("unchecked")
	public <T> T getPropertyValue(String identifier) {
		return (T) myPropertyBundle.getComponents().get(identifier).getValue();
	}

	@Override
	public void setProperty(String identifier, Object value) {
		myPropertyBundle.getComponents().get(identifier).setValue(value);
	}

	@Override
	public <T> void setProperty(Property<T> property) {
		myPropertyBundle.getComponents().put(property.getUniqueID(), property);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void setProperty(Property<T>... properties) {
		for (Property<T> prop : properties) {
			setProperty(prop);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getGroupName() {
		return ((Property<String>) myPropertyBundle.getComponents().get("groupID")).getValue();
	}

	public boolean hasProperty(String identifier) {
		return getProperties().getComponents().containsKey(identifier);
	}

	public static void main(String[] args) {
		Actor myActor = new Actor(null, ActorType.GLOBAL.toString());
		ActorDependencyInjector q = new ActorDependencyInjector(null);

		q.hookRelation(myActor);

		myActor.updateObservers(null);
	}
}