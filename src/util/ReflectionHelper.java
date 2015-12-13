package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Austin
 */
public class ReflectionHelper {
	
	/**
	 * Obtains the fields associated with an object
	 * 
	 * @param Object
	 * @return List of fields (public and private) corresponding to the input object
	 */
	public static Field[] getFields (Object o) {
		Class<?> clazz = o.getClass();
		Field[] myFields = clazz.getDeclaredFields();
		for (Field field : myFields) {
			field.setAccessible(true);
		}
		return myFields;
	}
	
	/**
	 * Returns the field for a given object given that the user knows the name of it
	 * 
	 * @param Object
	 * @param Field name
	 * @return Field associated with an object with the given field name
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	public static Field getField (Object o, String fieldString) 
			throws NoSuchFieldException, SecurityException {
		Class<? extends Object> parentClass = o.getClass();
		Field elementField = parentClass.getDeclaredField(fieldString);
		elementField.setAccessible(true);
		
		return elementField;
	}
	
	/**
	 * Obtains a list of fields corresponding to a specific class of field
	 * 
	 * @param Object
	 * @param clazz
	 * @return List of fields corresponding to a specific class
	 */
	public static List<Field> getTypedFields (Object o, Class<?> clazz) {
		Field[] myFields = getFields(o);
		List<Field> myTypedFields = new ArrayList<Field>();
		
		for (Field myField : myFields) {
			if (myField.getType() == clazz) {
				myTypedFields.add(myField);
			}
		}
		
		return myTypedFields;
	}
}
