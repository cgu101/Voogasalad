package authoring.events.interfaces;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ReflectionHelper;

/**
 * @author Austin
 */
public interface IWorker {
	public IAction getAction (String actionName);
	
	default Map<String, IAction> getActionMap () 
			throws IllegalArgumentException, IllegalAccessException {
		Map<String, IAction> myMap = new HashMap<>();
		List<Field> myActionFields = ReflectionHelper.getTypedFields(this, IAction.class);
		
		for (Field myActionField : myActionFields) {
			String myActionName = myActionField.getName();
			IAction myAction = (IAction) myActionField.get(this);
			myMap.put(myActionName, myAction);
		}
		
		return myMap;
	}
}
