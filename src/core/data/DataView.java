package core.data;

import org.w3c.dom.Node;

/**
 * Every data must implement this
 * 
 * @author Michael Schreiber
 *
 */
public interface DataView {
	
	/**
	 * convert item to XML Node
	 * 
	 * @return
	 */
	public Node toXMLNode();
	
	/**
	 * convert XML Node to item
	 * 
	 * @return
	 */
	public DataView fromXMLNode();
}