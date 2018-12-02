package utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Debug related stuff
 */
public class Debug {
	
	/**
	 * Globally enable / disable (debug mode).
	 */
	public static boolean enabled = false;

	/**
	 * Debug / prints HashMap<?, ?> (key and value).
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
	 * Wrapper for System.out.println().
	 * 
	 * @param text	the text
	 */
	public static void log(String text) {
		if (enabled) {
			System.out.println(text);
		}
	}
}