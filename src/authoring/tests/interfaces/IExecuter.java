package authoring.tests.interfaces;

public interface IExecuter extends IReaction, ICondition {
	default void execute () {
		if  (testCondition()) {
			react();
		}
	}
}
