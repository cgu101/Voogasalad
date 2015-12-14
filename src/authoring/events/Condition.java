package authoring.events;

import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;

import authoring.events.interfaces.ICondition;

/**
 * 
 * @author Austin
 *
 * @param <S>
 * @param <T>
 */
public class Condition<S, T> implements ICondition {

	/**
	 * Need a functional interface that is a supplier of boolean-valued results.
	 */
	private BooleanSupplier bS;
	
	public Condition (BiPredicate<S,T> conditions, S tests, T parameters) {
		bS = () -> conditions.test(tests, parameters);
	}
	
	@Override
	public boolean testCondition() {
		return bS.getAsBoolean();
	}

}
