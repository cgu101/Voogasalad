package authoring.events.interfaces;

/**
 * @author Austin
 */
public interface IExecuter extends IReaction, ICondition {
	default void execute () {
		if  (testCondition()) {
			react();
		}
	}
}
