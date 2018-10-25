package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Michael Schreiber
 *
 */
public class Debug {
	
	public static boolean enabled = false;

	/**
	 * debug / prints HashMap<?, ?> (key and value)
	 * 
	 * @param data	the HashMap<?, ?> data object
	 */
	public static void hashMap(HashMap<?, ?> data) {
		Iterator<?> iterator = data.entrySet().iterator();
	    while (iterator.hasNext()) {
	        Map.Entry<?, ?> pair = (Map.Entry<?, ?>) iterator.next();
	        Debug.log(pair.getKey() + " = " + pair.getValue());
	        iterator.remove();
	    }
	}
	
	/**
	 * wrapper for System.out.println
	 * 
	 * @param text	the text
	 */
	public static void log(String text) {
		if (enabled) {
			System.out.println(text);
		}
	}
}