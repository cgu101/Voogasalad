package authoring.tests;

import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;

import authoring.tests.interfaces.ICondition;

public class Condition<S, T> implements ICondition {

	/**
	 * Need a functional interface that is a supplier of boolean-valued results.
	 */
	private BooleanSupplier booleanSupplier;
	
	public Condition (BiPredicate<S,T> conditions, S tests, T parameters) {
		booleanSupplier = () -> conditions.test(tests, parameters);
	}
	
	@Override
	public boolean testCondition() {
		return booleanSupplier.getAsBoolean();
	}

}
