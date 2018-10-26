package core.data;

import java.util.HashMap;

import org.w3c.dom.Node;

/**
 * Save / load data to / from .xml based on objects which implement DataView interface
 * 
 * @author Michael Schreiber
 *
 */
public class Data {
	
	public static void save(DataView data) {
		Node node = data.toXMLNode();
	}
	
	public static DataView load() {
		return null;
	}
}